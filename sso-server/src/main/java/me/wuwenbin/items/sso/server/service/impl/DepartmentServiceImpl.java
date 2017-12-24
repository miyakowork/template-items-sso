package me.wuwenbin.items.sso.server.service.impl;

import me.wuwenbin.items.sso.dao.entity.Department;
import me.wuwenbin.items.sso.dao.repository.DepartmentRepository;
import me.wuwenbin.items.sso.server.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/22 at 17:53
 *
 * @author Wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository deptRepository;

    @Override
    public List<Department> findEnabledDepartments(String departmentId) {
        List<Department> departments = deptRepository.findByEnabled(true);
        String root = "0";
        if (root.equals(departmentId)) {
            departments.add(Department.root());
        }
        return departments;
    }

    @Override
    public boolean nodeIsParent(long nodeId) {
        return deptRepository.countByParentId(nodeId) > 0;
    }
}
