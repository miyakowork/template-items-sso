package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import me.wuwenbin.items.sso.service.service.RemoteService;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import me.wuwenbin.modules.utils.http.R;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.session.mgt.WebSessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * created by Wuwenbin on 2018/1/4 at 17:10
 *
 * @author wuwenbin
 */
@RestController
@RequestMapping("remote/api")
public class RemoteApiController extends BaseController {

    @Autowired
    private RemoteService remoteService;

    @RequestMapping(value = "authenticate/permissions", method = RequestMethod.POST)
    @ResourceScan("第三方app验权")
    public R authenticatePermissions(HttpServletRequest request,
                                     HttpServletResponse response,
                                     @RequestParam("permissionMarks[]") String[] permissionMarks,
                                     @RequestParam("accessToken") String accessToken,
                                     @RequestParam("logical") Logical logical) {
        SessionKey sessionKey = new WebSessionKey(accessToken, request, response);
        try {
            Session session = SecurityUtils.getSecurityManager().getSession(sessionKey);
            SimplePrincipalCollection spc = (SimplePrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            String shiroUser = (String) spc.getPrimaryPrincipal();
            String sessionUser = (String) session.getAttribute(ShiroConsts.SESSION_USERNAME_KEY);
            //同一个用户，应该都是样的因为存储的是一个东西（一个是shiro存的，一个是我们自己存的），防止万一做判断
            if (shiroUser.equals(sessionUser)) {
                User su = (User) session.getAttribute(ShiroConsts.SESSION_USER_KEY);
                Set<String> had = remoteService.findPermissions(su.getId(), su.getDefaultRoleId());
                if (remoteService.isAuthenticated(had, permissionMarks, logical)) {
                    return R.ok("验权成功！");
                } else {
                    return R.custom(903, "验权失败！");
                }
            }
            return R.error("发生未知错误，请重新认证！");
        } catch (ExpiredSessionException e) {
            return R.custom(902, "session 过期，请重新授权！");
        } catch (UnknownSessionException e) {
            String message = "access_token：[" + accessToken + "] 无效，请重新获取授权！错误信息：";
            return R.error(message.concat(e.getMessage()));
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }

    @RequestMapping(value = "authenticate/roleNames", method = RequestMethod.POST)
    @ResourceScan("第三方app验权")
    public R authenticateRoles(HttpServletRequest request,
                               HttpServletResponse response,
                               @RequestParam("roleNames[]") String[] roleNames,
                               @RequestParam("systemCode") String systemCode,
                               @RequestParam("accessToken") String accessToken,
                               @RequestParam("logical") Logical logical) {
        SessionKey sessionKey = new WebSessionKey(accessToken, request, response);
        try {
            Session session = SecurityUtils.getSecurityManager().getSession(sessionKey);
            String shiroUser = (String) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            String sessionUser = (String) session.getAttribute(ShiroConsts.SESSION_USERNAME_KEY);
            //同一个用户，应该都是样的因为存储的是一个东西（一个是shiro存的，一个是我们自己存的），防止万一做判断
            if (shiroUser.equals(sessionUser)) {
                User su = (User) session.getAttribute(ShiroConsts.SESSION_USER_KEY);
                Set<String> had = remoteService.findRoleNames(su.getId(), systemCode);
                if (remoteService.isAuthenticated(had, roleNames, logical)) {
                    return R.ok("验权成功！");
                } else {
                    return R.custom(903, "验权失败！");
                }
            }
            return R.error("发生未知错误，请重新认证！");
        } catch (ExpiredSessionException e) {
            return R.custom(902, "session 过期，请重新授权！");
        } catch (UnknownSessionException e) {
            String message = "access_token：[" + accessToken + "] 无效，请重新获取授权！错误信息：";
            return R.error(message.concat(e.getMessage()));
        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }


}
