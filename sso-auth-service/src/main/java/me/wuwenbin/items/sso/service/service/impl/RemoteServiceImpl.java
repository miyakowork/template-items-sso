package me.wuwenbin.items.sso.service.service.impl;

import me.wuwenbin.items.sso.dao.repository.ResourceRepository;
import me.wuwenbin.items.sso.dao.repository.RoleRepository;
import me.wuwenbin.items.sso.service.constant.CacheConsts;
import me.wuwenbin.items.sso.service.service.RemoteService;
import org.apache.shiro.authz.annotation.Logical;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * created by Wuwenbin on 2018/1/6 at 13:22
 *
 * @author wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RemoteServiceImpl implements RemoteService {

    @Resource
    private ResourceRepository resourceRepository;
    @Resource
    private RoleRepository roleRepository;

    @Override
    @Cacheable(value = CacheConsts.OAUTH_CACHE, key = "#userId+':'+#roleId")
    public Set<String> findPermissions(long userId, long roleId) {
        return new HashSet<>(resourceRepository.findPermissionByRoleId(roleId));
    }

    @Override
    @Cacheable(value = CacheConsts.OAUTH_CACHE, key = "#userId+':'+#systemCode")
    public Set<String> findRoleNames(long userId, String systemCode) {
        return new HashSet<>(roleRepository.findRoleNamesByUserIdAndSystemCode(userId, systemCode));
    }

    @Override
    public boolean isAuthenticated(Collection<String> hadPermissionOrRoles, String[] hasPermissionsOrRoles, Logical logical) {
        List<String> hasPermissionsOrRolesList = Arrays.asList(hasPermissionsOrRoles);
        if (logical.equals(Logical.AND)) {
            return hadPermissionOrRoles.containsAll(hasPermissionsOrRolesList);
        } else if (logical.equals(Logical.OR)) {
            for (String next : hasPermissionsOrRolesList) {
                if (hadPermissionOrRoles.contains(next)) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }
}
