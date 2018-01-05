package me.wuwenbin.items.sso.service.service.impl;

import me.wuwenbin.items.sso.dao.entity.Role;
import me.wuwenbin.items.sso.dao.repository.RoleRepository;
import me.wuwenbin.items.sso.service.service.RoleService;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * created by Wuwenbin on 2017/12/25 at 19:06
 *
 * @author wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository roleRepository;

    @Override
    public Set<String> findRoleNamesByUserId(long userId) {
        return new HashSet<>(roleRepository.findRoleNamesByUserId(userId));
    }

    @Override
    public Set<Role> findRolesByUserIdAndSystemCode(Long userId, String systemCode) {
        if (userId == null) {
            return new HashSet<>(roleRepository.findRolesByUserIdAndSystemCode(UserUtils.getLoginUser().getId(), systemCode));
        } else {
            return new HashSet<>(roleRepository.findRolesByUserIdAndSystemCode(userId, systemCode));
        }
    }

    @Override
    public Set<Role> findCurrentUserRolesInAllSystems(Long userId) {
        if (userId == null) {
            return new HashSet<>(roleRepository.findRolesByUserId(UserUtils.getLoginUser().getId()));
        } else {
            return new HashSet<>(roleRepository.findRolesByUserId(userId));
        }
    }
}
