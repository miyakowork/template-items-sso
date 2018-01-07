package me.wuwenbin.items.sso.service.service;

import org.apache.shiro.authz.annotation.Logical;

import java.util.Collection;
import java.util.Set;

/**
 * created by Wuwenbin on 2018/1/6 at 13:20
 *
 * @author wuwenbin
 */
public interface RemoteService {

    /**
     * 查找权限字符串集合
     *
     * @param userId
     * @param roleId
     * @return
     */
    Set<String> findPermissions(long userId, long roleId);

    /**
     * 查找角色名集合
     *
     * @param userId
     * @param systemCode
     * @return
     */
    Set<String> findRoleNames(long userId, String systemCode);

    /**
     * 验权
     *
     * @param permissionOrRoles
     * @param permissionsOrRoles
     * @param logical
     * @return
     */
    boolean isAuthenticated(Collection<String> permissionOrRoles, String[] permissionsOrRoles, Logical logical);
}
