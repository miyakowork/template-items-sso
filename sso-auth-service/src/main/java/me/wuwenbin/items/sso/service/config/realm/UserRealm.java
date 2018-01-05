package me.wuwenbin.items.sso.service.config.realm;

import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.repository.SystemModuleRepository;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.items.sso.service.constant.CacheConsts;
import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import me.wuwenbin.items.sso.service.service.PermissionService;
import me.wuwenbin.items.sso.service.service.RoleService;
import me.wuwenbin.items.sso.service.support.util.HttpUtils;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户权限认证
 *
 * @author wuwenbin
 */
public class UserRealm extends AuthorizingRealm implements CacheConsts {

    @Resource
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;


    /**
     * 授权 每次点击都会进行验证(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();
        User currentUser = userRepository.findByUsername(username);
        //获取当前用户的角色名称
        authorizationInfo.setRoles(roleService.findRoleNamesByUserId(currentUser.getId()));
        //获取当前用户默认角色
        long roleId = currentUser.getDefaultRoleId();
        authorizationInfo.setStringPermissions(permissionService.findPermissionsByRoleId(roleId));
        return authorizationInfo;
    }


    /**
     * 认证 (登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws RuntimeException {
        String username = (String) token.getPrincipal();
        if (StringUtils.isEmpty(username)) {
            throw new UnknownAccountException();
        }
        User user = userRepository.findByUsername(username);
        if (user == null) {
            //没找到帐号
            throw new UnknownAccountException();
        }
        HttpServletRequest request = HttpUtils.getRequest();
        List<SystemModule> systemModules = RepositoryFactory.get(SystemModuleRepository.class).findByUserCanLogin(username);
        if (systemModules != null) {
            //表示该用户仅有一个可登录的系统，直接让他登录此系统首页，不显示系统选择界面
            if (systemModules.size() == 1) {
                request.getSession().setAttribute(ShiroConsts.BEFORE_LOGIN_SUCCESS_URL, systemModules.get(0).getIndexUrl());
            }
            //其余的则表示该用户有多个可登录的系统，登陆成功之后显示系统选择界面
        } else {//表示该用户没有一个可以登录的系统，显示错误页面
            //TODO:转向错误提示页面（提示该用户没有一个系统可以让他登录）
            request.getSession().setAttribute(ShiroConsts.BEFORE_LOGIN_SUCCESS_URL, "/error/404");
        }

        if (Boolean.FALSE.equals(user.getEnabled())) {
            // 帐号锁定
            throw new LockedAccountException();
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        return new SimpleAuthenticationInfo(
                user.getUsername(),
                user.getPassword(),
                new MySimpleByteSource(user.getCredentialsSalt()),
                getName()
        );
    }


    /**
     * 清除授权
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清除认证
     *
     * @param principals
     */
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }


    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    private void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    private void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    @SuppressWarnings("unused")
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}