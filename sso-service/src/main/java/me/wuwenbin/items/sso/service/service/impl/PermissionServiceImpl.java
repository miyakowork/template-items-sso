package me.wuwenbin.items.sso.service.service.impl;

import me.wuwenbin.items.sso.dao.repository.ResourceRepository;
import me.wuwenbin.items.sso.service.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * created by Wuwenbin on 2017/12/25 at 19:01
 *
 * @author wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private ResourceRepository resourceRepository;

    @Override
    public Set<String> findPermissionsByRoleId(long roleId) {
        return new HashSet<>(resourceRepository.findPermissionByRoleId(roleId));
    }

    @Override
    public Set<String> findPermissionsByUserId(long userId) {
        return new HashSet<>(resourceRepository.findPermissionByUserId(userId));
    }
}
