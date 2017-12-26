package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.model.SelectBO;
import me.wuwenbin.items.sso.dao.repository.RoleRepository;
import me.wuwenbin.items.sso.dao.repository.SystemModuleRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.service.PermissionService;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Wuwenbin
 * @date 2017/8/1
 */
@RestController
@RequestMapping("oauth2")
public class SupportApiController extends BaseController {

    @Resource
    private SystemModuleRepository systemModuleRepository;
    @Autowired
    private PermissionService permissionService;
    @Resource
    private RoleRepository roleRepository;


    /**
     * 公共的下拉框搜索对象的值
     *
     * @return map
     */
    @RequestMapping("common/select")
    @RequiresPermissions("base:support:commonSelect")
    @ResourceScan("公共的下拉框搜索对象的值")
    public Map<Object, Object> searchSelect() {
        List<SelectBO> selectBOs = new ArrayList<>();
        SelectBO selectBO1 = SelectBO.create("1", "可用");
        selectBOs.add(selectBO1);
        SelectBO selectBO2 = SelectBO.create("0", "不可用");
        selectBOs.add(selectBO2);
        return searchSelect(selectBOs);
    }

    /**
     * 表格中使用系统模块查询的下拉框操作
     *
     * @return 下拉框对象
     */
    @RequestMapping("systemModule/select")
    @RequiresPermissions("base:support:commonSelect")
    @ResourceScan("表格中使用系统模块查询的下拉框操作")
    public Map<Object, Object> systemModuleSelect() {
        List<SelectBO> selectBOs = new ArrayList<>();
        List<SystemModule> systemModules = systemModuleRepository.findByEnabled(true);
        systemModules.forEach(sm -> {
            SelectBO selectBO = SelectBO.create(sm.getSystemCode(), sm.getName());
            selectBOs.add(selectBO);
        });
        return searchSelect(selectBOs);
    }

    /**
     * 获取当前登录用户的登录角色的所有权限标识名称
     *
     * @param roleId
     * @return
     */
    @RequestMapping("getPermissionsByRoleId")
    @RequiresPermissions("base:support:getPermissionsByRoleId")
    @ResourceScan("获取当前登录用户的登录角色的所有权限标识名称")
    public Collection<String> getPermissionsByRoleId(int roleId) {
        return permissionService.findPermissionsByRoleId(roleId);
    }

    /**
     * 获取当前登录用户的所有角色名称
     *
     * @param userId
     * @return
     */
    @RequestMapping("getRoleNamesByUserId")
    @RequiresPermissions("base:support:getRoleNamesByUserId")
    @ResourceScan("获取当前登录用户的所有角色名称")
    public Collection<String> getRoleNamesByUserId(int userId) {
        return roleRepository.findRoleNamesByUserId(userId);
    }

    /**
     * 转化为前端的select下拉框选择的对象
     *
     * @param selectBOS
     * @return
     */
    private static Map<Object, Object> searchSelect(List<SelectBO> selectBOS) {
        Map<Object, Object> map = new LinkedHashMap<>();
        selectBOS.forEach(sb -> map.put(sb.getValue(), sb.getText()));
        return map;
    }
}
