package me.wuwenbin.items.sso.server.service;

import me.wuwenbin.items.sso.dao.entity.Department;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/22 at 17:48
 *
 * @author Wuwenbin
 */
public interface DepartmentService {

    /**
     * 根据部门id查找当此部门之下的所有可用的部门
     *
     * @param departmentId
     * @return
     */
    List<Department> findEnabledDepartments(String departmentId);

    /**
     * 判断此部门节点是否为父节点
     *
     * @param nodeId
     * @return
     */
    boolean nodeIsParent(long nodeId);
}
