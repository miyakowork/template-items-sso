package me.wuwenbin.items.sso.service.service.impl;

import me.wuwenbin.items.sso.dao.entity.PrivilegeOperation;
import me.wuwenbin.items.sso.dao.entity.PrivilegePage;
import me.wuwenbin.items.sso.dao.entity.RoleResource;
import me.wuwenbin.items.sso.dao.repository.PrivilegeOperationRepository;
import me.wuwenbin.items.sso.dao.repository.PrivilegePageRepository;
import me.wuwenbin.items.sso.dao.repository.RoleResourceRepository;
import me.wuwenbin.items.sso.service.model.Ztree;
import me.wuwenbin.items.sso.service.service.PrivilegeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * created by Wuwenbin on 2017/12/25 at 16:33
 *
 * @author wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PrivilegeServiceImpl implements PrivilegeService {

    @Resource
    private PrivilegePageRepository privilegePageRepository;
    @Resource
    private PrivilegeOperationRepository privilegeOperationRepository;
    @Resource
    private RoleResourceRepository roleResourceRepository;

    @Override
    public List<Ztree> getPrivilegeData(String resourceModuleId, String roleId) {
        List<PrivilegePage> privilegePageList = privilegePageRepository.findByResourceModuleId(resourceModuleId);
        List<String> resourceIdList = roleResourceRepository.findResourceIdByRoleIdAndEnabled(roleId, true);
        List<Ztree> pagePrivilegeTree = new LinkedList<>();
        // page_privilege层次
        privilegePageList.forEach(pp -> {
            Ztree pageTree = Ztree.builder()
                    .id(pp.getId().toString())
                    .name(pp.getName())
                    .open(false)
                    .pId(pp.getResourceModuleId().toString())
                    .resourceId(pp.getResourceId().toString())
                    .isParent(false)
                    .other("page_privilege").build();
            resourceIdList.forEach(resId -> {
                if (resId.equals(pp.getResourceId().toString())) {
                    pageTree.setChecked(true);
                }
            });
            pagePrivilegeTree.add(pageTree);

            // 查找页面内的操作级权限
            List<PrivilegeOperation> privilegeOperationList = privilegeOperationRepository.findByPagePrivilegeId(pp.getId());
            privilegeOperationList.forEach(po -> {
                Ztree operationTree = Ztree.builder()
                        .id(po.getId().toString())
                        .name(po.getOperationName())
                        .open(false)
                        .pId(po.getPagePrivilegeId().toString())
                        .resourceId(po.getResourceId().toString())
                        .isParent(false)
                        .other("operation_privilege").build();
                resourceIdList.forEach(resId -> {
                    if (resId.equals(po.getResourceId().toString())) {
                        operationTree.setChecked(true);
                    }
                });
                pagePrivilegeTree.add(operationTree);
            });
        });
        return pagePrivilegeTree;
    }

    @Override
    public void setPrivilege(String[] resourceIds, String roleId, boolean checked) throws Exception {
        Collection<RoleResource> roleResources = new ArrayList<>(resourceIds.length);
        Arrays.stream(resourceIds).forEach(resId -> {
            RoleResource rr = RoleResource.builder().roleId(Long.valueOf(roleId)).resourceId(Long.valueOf(resId)).enabled(true).build();
            roleResources.add(rr);
        });
        if (checked) {
            roleResourceRepository.save(roleResources);
        } else {
            roleResourceRepository.deleteByRoleIdAndResourceId(roleResources);
        }
    }
}
