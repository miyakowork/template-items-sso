package me.wuwenbin.items.sso.service.service;

import me.wuwenbin.items.sso.dao.entity.Role;

import java.util.Set;

/**
 * created by Wuwenbin on 2017/12/25 at 19:05
 *
 * @author wuwenbin
 */
public interface RoleService {

    /**
     * 根据用户id查询当前用户所有的角色名称集合信息
     *
     * @param userId 用户id
     * @return 用户角色名称集合
     */
    Set<String> findRoleNamesByUserId(long userId);

    /**
     * 查询当前登录用户的所有角色名
     *
     * @param userId
     * @param systemCode
     * @return 当前用户的所有角色信息集合
     */
    Set<Role> findRolesByUserIdAndSystemCode(Long userId, String systemCode);

    /**
     * 查询当前登录用户所有用户名在所有的系统中
     *
     * @param userId
     * @return
     */
    Set<Role> findCurrentUserRolesInAllSystems(Long userId);
}
