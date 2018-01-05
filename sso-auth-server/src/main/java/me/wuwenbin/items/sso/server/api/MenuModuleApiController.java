package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.MenuModule;
import me.wuwenbin.items.sso.dao.model.pagevo.MenuModuleVo;
import me.wuwenbin.items.sso.dao.model.querybo.MenuModuleBo;
import me.wuwenbin.items.sso.dao.repository.MenuModuleRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.bootstrap.BootstrapTable;
import me.wuwenbin.modules.pagination.util.StringUtils;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import me.wuwenbin.modules.utils.bean.ArrayConverts;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.web.Controllers;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Wuwenbin
 * @date 2017/08/01
 */
@RestController
@RequestMapping("oauth2/menuModule/api")
public class MenuModuleApiController extends BaseController {

    @Resource
    private MenuModuleRepository menuModuleRepository;


    /**
     * 菜单模块页面
     *
     * @param page         page
     * @param menuModuleBo 搜索对象
     * @return bootstrapTable
     */
    @RequestMapping("list")
    @RequiresPermissions("base:menuModule:list")
    @ResourceScan("获取菜单模块页面的数据")
    public BootstrapTable<MenuModuleVo> list(Page<MenuModuleVo> page, MenuModuleBo menuModuleBo) {
        page = menuModuleRepository.findPagination(page, MenuModuleVo.class, menuModuleBo);
        return bootstrapTable(page);
    }

    /**
     * 添加新的菜单模块
     *
     * @param menuModule 添加的对象
     * @return R
     */
    @RequestMapping("add")
    @RequiresPermissions("base:menuModule:add")
    @ResourceScan("添加新的菜单模块操作")
    public R add(MenuModule menuModule) {
        menuModule.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加菜单模块").execLight(menuModule, (mm) -> menuModuleRepository.save(mm));
    }


    /**
     * 修改菜单模块
     *
     * @param menuModule 修改的对象
     * @return R
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:menuModule:edit")
    @ResourceScan("修改菜单模块操作")
    public R doEdit(MenuModule menuModule) {
        menuModule.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("修改菜单模块").execLight(menuModule, (mm) -> menuModuleRepository.updateById(menuModule));
    }

    /**
     * 删除菜单模块
     *
     * @param ids
     * @return R
     */
    @RequestMapping("delete")
    @RequiresPermissions("base:menuModule:delete")
    @ResourceScan("删除菜单模块操作")
    public R delete(String ids) {
        long[] idArray = ArrayConverts.toLongArray(ids.split(","));
        List<Long> longs = Arrays.stream(idArray).boxed().collect(Collectors.toList());
        return Controllers.builder("删除菜单模块").execLight(longs, (idLongs) -> menuModuleRepository.delete(idLongs));
    }


    /**
     * 查找可用的菜单模块集合
     *
     * @return
     */
    @RequestMapping("find/enables")
    @RequiresPermissions("base:menuModule:enables")
    @ResourceScan("查找可用的菜单模块集合操作")
    public List<MenuModule> findEnabledMenuModules(String systemModuleCode) {
        if (StringUtils.isNotEmpty(systemModuleCode)) {
            return menuModuleRepository.findMenuModuleByRoleIdAndSystemCode(UserUtils.getLoginUser().getDefaultRoleId(), systemModuleCode);
        } else {
            return menuModuleRepository.findByEnabled(true);
        }
    }
}
