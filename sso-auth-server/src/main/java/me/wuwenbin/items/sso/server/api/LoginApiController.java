package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.entity.UserLoginLog;
import me.wuwenbin.items.sso.dao.repository.UserLoginLogRepository;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import me.wuwenbin.items.sso.service.support.util.HttpUtils;
import me.wuwenbin.items.sso.service.support.util.ShiroUtils;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.pagination.util.StringUtils;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import me.wuwenbin.modules.utils.http.R;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 登录API控制层接口
 *
 * @author wuwenbin
 * @date 2017/5/25
 */
@RestController
public class LoginApiController {

    private static Logger LOG = LoggerFactory.getLogger(LoginApiController.class);

    @Autowired
    private SecurityManager securityManager;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public R doLogin(HttpServletRequest request, String userName, String userPass) {

        String username = UserUtils.getLoginUserName();
        if (StringUtils.isNotEmpty(username)) {
            LOG.info("用户 [" + username + "] 已登录，跳转至首页");
            return R.ok("请勿重复登录");
        }
        String exceptionName = (String) request.getAttribute(ShiroConsts.LOGIN_FAILURE);
        if (UnknownAccountException.class.getName().equals(exceptionName)) {
            LOG.error("帐号不存在");
            return R.error("账号/密码错误");
        } else if (IncorrectCredentialsException.class.getName().equals(exceptionName)) {
            LOG.error("密码/凭证错误");
            return R.error("账号/密码错误");
        } else if (ExcessiveAttemptsException.class.getName().equals(exceptionName)) {
            LOG.error("验证失败次数超过限制");
            return R.error("输入错误次数过多，请稍后尝试");
        } else if (LockedAccountException.class.getName().equals(exceptionName)) {
            LOG.error("帐号已被锁定");
            return R.error("帐号被锁定");
        } else if (AuthenticationException.class.getName().equals(exceptionName)) {
            LOG.error("帐号验证失败");
            return R.error("账户验证失败");
        } else if (exceptionName != null) {
            LOG.error("未知的错误：" + exceptionName);
            return R.error("发生未知错误");
        }

        Subject subject = SecurityUtils.getSubject();
        if (subject.getSession().getAttribute(ShiroConsts.SESSION_FORCE_LOGOUT_KEY) != null) {
            LOG.error("已被管理员强制退出");
            return R.error("您已被管理员强制退出，请重新登录");
        }
        if (subject.isAuthenticated() && !subject.isRemembered()) {
            return R.ok("登录成功");
        } else {
            if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(userPass)) {
                return R.error("非法操作！");
            } else {
                return doTokenLogin(request, userName, userPass);
            }
        }
    }


    private R doTokenLogin(HttpServletRequest request, String username, String password) {
        try {
            User user = RepositoryFactory.get(UserRepository.class).findByUsername(username);
            if (user == null) {
                return R.error("账号或密码不正确！");
            }
            SecurityUtils.setSecurityManager(securityManager);
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), HttpUtils.getRemoteAddr(request));
            subject.login(token);
            Session session = ShiroUtils.getSession();
            //验证用户成功了，把用户名信息放到session中去
            session.setAttribute(ShiroConsts.SESSION_USERNAME_KEY, username);
            //验证用户成功了，删除强制登录的标识
            session.removeAttribute(ShiroConsts.SESSION_FORCE_LOGOUT_KEY);
            traceLoginLog(request, username);
            return R.ok("login success").put("access_token", session.getId());
        } catch (UnknownAccountException e) {
            LOG.error("帐号不存在");
            return R.error("账号/密码错误");
        } catch (IncorrectCredentialsException e) {
            LOG.error("密码/凭证错误");
            return R.error("账号/密码错误");
        } catch (ExcessiveAttemptsException e) {
            LOG.error("验证失败次数超过限制");
            return R.error("输入错误次数过多，请稍后尝试");
        } catch (LockedAccountException e) {
            LOG.error("帐号已被锁定");
            return R.error("帐号被锁定");
        } catch (AuthenticationException e) {
            LOG.error("帐号验证失败");
            return R.error("账户验证失败");
        } catch (Exception e) {
            LOG.error("未知的错误：" + e.getClass().getName());
            return R.error("发生未知错误");
        }
    }


    /**
     * 记录日志
     *
     * @param request
     * @param username
     * @throws Exception
     */
    private static void traceLoginLog(HttpServletRequest request, String username) throws Exception {
        Long userId = RepositoryFactory.get(UserRepository.class).findByUsername(username).getId();
        UserLoginLog userLoginLog = UserLoginLog.builder()
                .userId(userId)
                .lastLoginDate(LocalDateTime.now())
                .lastLoginIp(HttpUtils.getRemoteAddr(request))
                .build();
        userLoginLog.setEnabled(true);
        userLoginLog.setUpdateDate(LocalDateTime.now());
        RepositoryFactory.get(UserLoginLogRepository.class).save(userLoginLog);
    }

}
