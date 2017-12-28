package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.Department;
import me.wuwenbin.items.sso.dao.model.pagevo.DepartmentVo;
import me.wuwenbin.items.sso.dao.model.querybo.DepartmentBo;
import me.wuwenbin.items.sso.dao.repository.DepartmentRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.model.Ztree;
import me.wuwenbin.items.sso.service.service.DepartmentService;
import me.wuwenbin.items.sso.service.service.common.PublicServices;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.bootstrap.BootstrapTable;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.web.Controllers;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 部门REST控制层
 *
 * @author wuwenbin
 * @date 2017/7/12
 */
@RestController
@RequestMapping("oauth2/department/api")
public class DepartmentApiController extends BaseController {

    @Resource
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentService departmentService;


    /**
     * 部门页面数据
     *
     * @param page         page
     * @param departmentBo 部门查询字段
     * @return page
     */
    @RequestMapping("list")
    @RequiresPermissions("base:department:list")
    @ResourceScan("获取部门列表数据")
    public BootstrapTable<DepartmentVo> departments(Page<DepartmentVo> page, DepartmentBo departmentBo) {
        page = departmentRepository.findPagination(page, DepartmentVo.class, departmentBo);
        return bootstrapTable(page);
    }

    /**
     * 生成根节点对应一级zTree
     *
     * @return json R
     */
    @RequestMapping("selectDepartment")
    @RequiresPermissions("base:department:selectDepartment")
    @ResourceScan("获取部门树形结构操作")
    public List<Ztree> selectDepartment(String id) {
        return PublicServices.model2Ztree(departmentService.findEnabledDepartments(id));
    }

    /**
     * 添加新的部门
     *
     * @param department 新部门对象信息
     * @return R
     */
    @RequestMapping("add")
    @RequiresPermissions("base:department:add")
    @ResourceScan("添加部门操作")
    public R add(Department department) {
        department.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加部门").exec(() -> departmentRepository.save(department) != null);
    }

    /**
     * 编辑部门信息
     *
     * @param department 编辑的部门对象
     * @return R
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:department:edit")
    @ResourceScan("编辑部门操作")
    public R edit(Department department) {
        department.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("编辑部门").exec(() -> departmentRepository.updateById(department) == 1);
    }


}
