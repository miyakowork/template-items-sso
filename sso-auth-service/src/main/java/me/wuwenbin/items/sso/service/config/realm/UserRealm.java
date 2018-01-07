package me.wuwenbin.items.sso.service.config.realm;

import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.repository.ResourceRepository;
import me.wuwenbin.items.sso.dao.repository.RoleRepository;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.items.sso.service.config.token.MyUsernamePasswordToken;
import me.wuwenbin.items.sso.service.constant.CacheConsts;
import me.wuwenbin.items.sso.service.service.RoleService;
import me.wuwenbin.items.sso.service.support.util.HttpUtils;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

/**
 * 用户权限认证
 *
 * @author wuwenbin
 */
public class UserRealm extends AuthorizingRealm implements CacheConsts {

    private static Logger LOG = LoggerFactory.getLogger(UserRealm.class);

    @Resource
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Resource
    private ResourceRepository resourceRepository;


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
        authorizationInfo.setStringPermissions(new HashSet<>(resourceRepository.findPermissionByRoleId(roleId)));
        return authorizationInfo;
    }


    /**
     * 认证 (登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws RuntimeException {
        String username = (String) token.getPrincipal();
        if (StringUtils.isEmpty(username)) {
            LOG.error("用户名不能为空");
            throw new UnknownAccountException();
        }

        User user = userRepository.findByUsername(username);
        if (user == null) {
            LOG.error("用户：[{}] 不存在", username);
            throw new UnknownAccountException();
        }

        HttpServletRequest request = HttpUtils.getRequest();
        MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) token;
        String loginFrom = myToken.getFrom();
        //如果不是从sso-auth-server登录的，则是调用api登录，那么需要传入systemCode参数
        if (!"uls".equals(loginFrom)) {
            Object systemCode = request.getParameter("systemCode");
            if (StringUtils.isEmpty(systemCode)) {
                LOG.error("系统代码不能为空");
                throw new UnknownAccountException();
            }
            long defaultRoleId = user.getDefaultRoleId();
            String userInSystem = RepositoryFactory.get(RoleRepository.class).findSystemCodeById(defaultRoleId);
            if (!systemCode.equals(userInSystem)) {
                LOG.error("账号：[{}] 无权登录系统：[{}]", username, systemCode);
                throw new UnknownAccountException();
            }
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
