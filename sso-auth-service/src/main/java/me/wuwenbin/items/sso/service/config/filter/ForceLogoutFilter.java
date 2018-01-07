package me.wuwenbin.items.sso.service.config.filter;

import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 强制退出拦截器
 *
 * @author wuwenbin
 * @date 2017/8/9
 */
@Component
public class ForceLogoutFilter extends AccessControlFilter implements TemplateFilter {

    private static Logger LOG = LoggerFactory.getLogger(ForceLogoutFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = WebUtils.toHttp(request);
        String uri = req.getRequestURI();
        String method = req.getMethod();
        LOG.info("-- ForceLogoutFilter，访问URI:[{}]，请求方式:[{}]", uri, method);

        Session session = getSubject(request, response).getSession(false);
        if (session != null) {
            Object forceLogoutFlag = session.getAttribute(ShiroConsts.SESSION_FORCE_LOGOUT_KEY);
            return forceLogoutFlag == null;
        }
        return false;
    }

    /**
     * 访问拒绝就停职拦截器链的执行，停止执行下一个拦截器，到此为止
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Subject subject = getSubject(servletRequest, servletResponse);
        //如果shiro session不为空，则强制退出
        if (subject.getSession(false) != null) {
            try {
                //强制退出
                subject.logout();
            } catch (Exception e) {/*ignore exception*/}
        }

        //退出之后开始确定登录url
        String loginUrl = getLoginUrl();
        String message = "请重新登录！";
        //如果不为空，则确定是被强制退出的，loginUrl加上forceLogout参数
        if (subject.getSession().getAttribute(ShiroConsts.SESSION_FORCE_LOGOUT_KEY) != null) {
            loginUrl += (getLoginUrl().contains("?") ? "&" : "?") + "forceLogout=" + UUID.randomUUID().toString().replace("-", "");
            message = "您已被强制退出，请重新登录！";
        }
        return denyControl(servletRequest, servletResponse, message, loginUrl);
    }
}
