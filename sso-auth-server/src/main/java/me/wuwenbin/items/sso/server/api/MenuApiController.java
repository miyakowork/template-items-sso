package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.Menu;
import me.wuwenbin.items.sso.dao.repository.MenuRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.model.Ztree;
import me.wuwenbin.items.sso.service.service.MenuModuleService;
import me.wuwenbin.items.sso.service.service.common.PublicServices;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.pagination.util.StringUtils;
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
 * @author Wuwenbin
 * @date 2017/8/21
 */
@RestController
@RequestMapping("oauth2/menu/api")
public class MenuApiController extends BaseController {

    @Autowired
    private MenuModuleService menuModuleService;
    @Resource
    private MenuRepository menuRepository;


    /**
     * 菜单表格树
     *
     * @param roleId
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("base:menu:list")
    @ResourceScan("获取菜单表格树数据")
    public List<Menu> findMenuTable(String roleId) {
        return menuRepository.findByRoleIdAndEnabled(roleId, true);
    }

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     */
    @RequestMapping("add")
    @RequiresPermissions("base:menu:add")
    @ResourceScan("添加菜单操作")
    public R add(Menu menu) {
        return Controllers.builder("添加菜单").exec(() -> menuModuleService.addMenu(menu));
    }

    /**
     * 编辑菜单
     *
     * @param menu
     * @return
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:menu:edit")
    @ResourceScan("编辑菜单操作")
    public R edit(Menu menu) {
        return Controllers.builder("编辑菜单").exec(() -> menuModuleService.editMenu(menu));
    }

    /**
     * 删除菜单
     *
     * @param id
     * @return
     */
    @RequestMapping("delete")
    @RequiresPermissions("base:menu:delete")
    @ResourceScan("删除菜单操作")
    public R delete(Long id) {
        return Controllers.builder("删除菜单").execLight(id, (o) -> menuModuleService.deleteMenu(o));
    }

    /**
     * 查找菜单树
     *
     * @return
     */
    @RequestMapping("menuTree")
    @RequiresPermissions("base:menu:menuTree")
    @ResourceScan("根据菜单模块查找菜单树操作")
    public List<Ztree> findMenuTree(String menuModuleId, String roleId) {
        if (StringUtils.isNotEmpty(menuModuleId)) {
            return PublicServices.model2Ztree(menuRepository.findByMenuModuleIdAndRoleIdAndEnabled(menuModuleId, roleId, true));
        } else {
            return PublicServices.model2Ztree(menuRepository.findByEnabled(true));
        }
    }

    /**
     * 添加菜单时候选择内置权限菜单时，查找带回选择的权限资源
     *
     * @param resourceModuleId
     * @param roleId
     * @return
     */
    @RequestMapping("addMenuRes")
    @RequiresPermissions("base:menu:addMenuRes")
    @ResourceScan("添加菜单查找带回的权限资源")
    public List<Ztree> findAddMenuPrivilegeResource(String resourceModuleId, String roleId) {
        return menuModuleService.findSelectMenuPrivilege(resourceModuleId, roleId);
    }

    /**
     * 首页获取左侧菜单
     *
     * @param menuModuleId
     * @param systemCode
     * @return
     */
    @RequestMapping("getMenuListIndex")
    @RequiresPermissions("base:menu:getMenuListIndex")
    @ResourceScan("首页获取左侧菜单")
    public List<Menu> getMenuListIndex(int menuModuleId, String systemCode) {
        return menuModuleService.findLeftMenuCached(UserUtils.getLoginUser().getDefaultRoleId(), menuModuleId, systemCode);
    }
}
