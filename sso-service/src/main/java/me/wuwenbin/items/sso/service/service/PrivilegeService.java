package me.wuwenbin.items.sso.service.service;

import me.wuwenbin.items.sso.service.model.Ztree;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/25 at 16:31
 *
 * @author wuwenbin
 */
public interface PrivilegeService {

    /**
     * '
     * 异步获取的权限资源树
     *
     * @param resourceModuleId
     * @param roleId
     * @return
     */
    List<Ztree> getPrivilegeData(String resourceModuleId, String roleId);

    /**
     * 分配权限操作
     *
     * @param resourceIds
     * @param roleId
     * @param checked
     * @throws Exception
     */
    void setPrivilege(String[] resourceIds, String roleId, boolean checked) throws Exception;
}
