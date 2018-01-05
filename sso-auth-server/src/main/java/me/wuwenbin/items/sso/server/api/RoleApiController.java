package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.Role;
import me.wuwenbin.items.sso.dao.model.pagevo.RoleVo;
import me.wuwenbin.items.sso.dao.model.querybo.RoleBo;
import me.wuwenbin.items.sso.dao.repository.RoleRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.model.Ztree;
import me.wuwenbin.items.sso.service.service.common.PublicServices;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.bootstrap.BootstrapTable;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.web.Controllers;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author tuchen
 * @date 2017/7/13
 */
@RestController
@RequestMapping("/oauth2/role/api")
public class RoleApiController extends BaseController {

    @Resource
    private RoleRepository roleRepository;

    /**
     * 角色对象的page
     *
     * @param roleBo 角色查询控件对应的对象
     * @param page   page
     * @return BootstrapTable
     */
    @RequestMapping("list")
    @RequiresPermissions("base:role:tree")
    @ResourceScan("获取角色列表页面的数据")
    public BootstrapTable<RoleVo> rolePage(RoleBo roleBo, Page<RoleVo> page) {
        page = roleRepository.findPagination(page, RoleVo.class, roleBo);
        return bootstrapTable(page);
    }

    /**
     * 添加一个新角色
     *
     * @param role role
     * @return R
     */
    @RequestMapping("add")
    @RequiresPermissions("base:role:add")
    @ResourceScan("添加一个新角色的操作")
    public R add(Role role) {
        role.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加角色").exec(() -> roleRepository.save(role) != null);
    }

    /**
     * 修改一个角色对象
     *
     * @param role role
     * @return R
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:role:edit")
    @ResourceScan("修改一个角色对象的操作")
    public R edit(Role role) {
        role.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("修改角色信息").exec(() -> roleRepository.updateById(role) == 1);
    }

    /**
     * 根据父节点和系统模块获取该节点下面的树数据
     *
     * @return json R
     */
    @RequestMapping("selectRole")
    @RequiresPermissions("base:role:selectRole")
    @ResourceScan("根据父节点和系统模块获取该节点下面的树数据")
    public List<Ztree> selectRoleZTree(String roleId, String systemModuleCode) {
        List<Role> roles = roleRepository.findBySystemCodeAndEnabled(systemModuleCode, true);
        if ("0".equalsIgnoreCase(roleId)) {
            roles.add(Role.root());
        }
        return PublicServices.model2Ztree(roles);
    }


    @RequestMapping("findAllRoles")
    @RequiresPermissions("base:role:findAllRoles")
    @ResourceScan("查找出所有的角色数据")
    public List<Map<String, Object>> findAllRoles() {
        return roleRepository.findAllRoles();
    }


}
