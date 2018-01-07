package me.wuwenbin.items.sso.service.config.filter;

import jodd.json.JsonSerializer;
import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.entity.UserLoginLog;
import me.wuwenbin.items.sso.dao.repository.SystemModuleRepository;
import me.wuwenbin.items.sso.dao.repository.UserLoginLogRepository;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.items.sso.service.config.token.MyUsernamePasswordToken;
import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import me.wuwenbin.items.sso.service.support.util.HttpUtils;
import me.wuwenbin.items.sso.service.utils.FilterUtils;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import me.wuwenbin.modules.utils.http.R;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用于验证码验证的 Shiro 拦截器在用于身份认证的拦截器之前运行;
 * 但是如果验证码验证 拦截器失败了，就不需要进行身份认证拦截器流程了;
 * 所以需要修改下如 FormAuthenticationFilter 身份认证拦截器，当验证码验证失败时不再走身份认证拦截器。
 * Created by wuwenbin on 2017/2/22.
 *
 * @author wuwenbin
 * @since 1.0
 */
@Component
public class MyFormAuthenticationFilter extends FormAuthenticationFilter implements TemplateFilter {

    private static final Logger LOG = LoggerFactory.getLogger(MyFormAuthenticationFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String method = req.getMethod();
        LOG.info("-- FormAuthenticationFilter，访问URI：[{}]，请求方式：[{}]", uri, method);

        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected AuthenticationToken createToken(String username, String password, ServletRequest request, ServletResponse response) {
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        MyUsernamePasswordToken upt = new MyUsernamePasswordToken(username, password, rememberMe, host);
        //login from user-login-server
        upt.setFrom("uls");
        return upt;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        //验证用户成功了，把用户名信息放到session中去
        subject.getSession().setAttribute(ShiroConsts.SESSION_USERNAME_KEY, token.getPrincipal());
        User user = RepositoryFactory.get(UserRepository.class).findByUsername((String) token.getPrincipal());
        subject.getSession().setAttribute(ShiroConsts.SESSION_USER_KEY, user);
        //验证用户成功了，删除强制登录的标识
        subject.getSession().removeAttribute(ShiroConsts.SESSION_FORCE_LOGOUT_KEY);

        String successUrl = getSuccessUrl();
        List<SystemModule> systemModules = RepositoryFactory.get(SystemModuleRepository.class).findByUserCanLogin((String) token.getPrincipal());
        if (systemModules != null) {
            //表示该用户仅有一个可登录的系统，直接让他登录此系统首页，不显示系统选择界面
            if (systemModules.size() == 1) {
                successUrl = systemModules.get(0).getIndexUrl();
            } else {
                //其余的则表示该用户有多个可登录的系统，登陆成功之后显示系统选择界面
                subject.getSession().setAttribute("systemModules", systemModules);
            }
        } else {
            //表示该用户没有一个可以登录的系统，显示错误页面
            successUrl = "/error/404";
        }
        traceLoginLog(httpServletRequest, user);
        if (!HttpUtils.isAjax(httpServletRequest)) {
            WebUtils.getAndClearSavedRequest(request);
            WebUtils.redirectToSavedRequest(request, response, successUrl);
            return false;
        } else {
            ajaxLoginSuccess(httpServletResponse, successUrl);
            return false;
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("检测到登录操作，尝试执行登录.");
                }
                return executeLogin(request, response);
            } else {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("转向登录页面");
                }
                //allow them to see the login page ;)
                return true;
            }
        } else {
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            if (HttpUtils.isAjax(httpServletRequest)) {
                if (HttpUtils.isRouter(httpServletRequest)) {
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("尝试访问一个需要认证身份信息的路由. 跳转至认证路由：[" + ShiroConsts.LOGIN_ROUTER + "]");
                    }
                    saveRequest(request);
                    WebUtils.issueRedirect(request, response, ShiroConsts.LOGIN_ROUTER);
                } else {
                    HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
                    if (LOG.isTraceEnabled()) {
                        LOG.trace("尝试访问一个需要认证身份信息的路由. 跳转至认证地址：[" + getLoginUrl() + "]");
                    }
                    saveRequest(request);
                    FilterUtils.ajaxControl(httpServletResponse, "请登陆后再操作！");
                }
            } else {
                if (LOG.isTraceEnabled()) {
                    LOG.trace("尝试访问一个需要认证身份信息的路由. 跳转至认证地址：[" + getLoginUrl() + "]");
                }
                saveRequestAndRedirectToLogin(request, response);
            }
            return false;
        }
//        return super.onAccessDenied(request, response);
    }


    private static void ajaxLoginSuccess(HttpServletResponse httpServletResponse, String uri) throws IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        PrintWriter out = httpServletResponse.getWriter();
        String json = new JsonSerializer().include("code", "message", "data").serialize(R.ok("登录成功", uri));
        out.println(json);
        out.flush();
        out.close();
    }

    private static void traceLoginLog(HttpServletRequest httpServletRequest, User u) {
        try {
            Long userId = u.getId();
            UserLoginLog userLoginLog = UserLoginLog.builder().userId(userId).lastLoginDate(LocalDateTime.now()).lastLoginIp(HttpUtils.getRemoteAddr(httpServletRequest)).build();
            userLoginLog.setEnabled(true);
            userLoginLog.setUpdateDate(LocalDateTime.now());
            RepositoryFactory.get(UserLoginLogRepository.class).save(userLoginLog);
        } catch (Exception e) {
            LOG.error("记录用户登录日志失败，异常信息：{}", e);
        }
    }

}
