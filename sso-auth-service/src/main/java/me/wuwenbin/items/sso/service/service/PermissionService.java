package me.wuwenbin.items.sso.service.service;

import java.util.Set;

/**
 * created by Wuwenbin on 2017/12/25 at 19:00
 *
 * @author wuwenbin
 */
public interface PermissionService {

    /**
     * 通过角色id查找当前角色id的所拥有的权限标识
     *
     * @param roleId
     * @return
     */
    Set<String> findPermissionsByRoleId(long roleId);

    /**
     * 通过用户id查找当前用户id的所拥有的权限表示
     *
     * @param userId
     * @return
     */
    Set<String> findPermissionsByUserId(long userId);
}
