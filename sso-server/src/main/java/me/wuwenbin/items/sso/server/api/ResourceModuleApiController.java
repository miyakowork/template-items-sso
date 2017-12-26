package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.ResourceModule;
import me.wuwenbin.items.sso.dao.model.pageVo.ResourceModuleVo;
import me.wuwenbin.items.sso.dao.model.queryBo.ResModuleBo;
import me.wuwenbin.items.sso.dao.repository.ResourceModuleRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.model.Ztree;
import me.wuwenbin.items.sso.service.service.common.PublicServices;
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
 * 资源模块的REST控制层
 *
 * @author wuwenbin
 * @date 2017/8/8
 */
@RestController
@RequestMapping("/oauth2/resModule/api")
public class ResourceModuleApiController extends BaseController {

    @Resource
    private ResourceModuleRepository resourceModuleRepository;

    /**
     * 资源模块管理页面
     *
     * @param resModuleBo
     * @param page
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("base:resModule:list")
    @ResourceScan("获取资源模块管理页面的数据")
    public BootstrapTable<ResourceModuleVo> oauthResourceModule(ResModuleBo resModuleBo, Page<ResourceModuleVo> page) {
        page = resourceModuleRepository.findPagination(page, ResourceModuleVo.class, resModuleBo);
        return bootstrapTable(page);
    }

    /**
     * 添加新资源模块
     *
     * @param resourceModule 添加的资源模块
     * @return R
     */
    @RequestMapping("add")
    @RequiresPermissions("base:resModule:add")
    @ResourceScan("添加新资源模块操作")
    public R add(ResourceModule resourceModule) {
        resourceModule.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加资源模块").exec(() -> resourceModuleRepository.save(resourceModule) != null);
    }

    /**
     * 修改资源模块
     *
     * @param resourceModule 修改的资源模块
     * @return R
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:resModule:edit")
    @ResourceScan("修改资源模块操作")
    public R edit(ResourceModule resourceModule) {
        resourceModule.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("修改资源模块").exec(() -> resourceModuleRepository.updateById(resourceModule) == 1);
    }

    /**
     * 删除资源模块
     *
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @RequiresPermissions("base:resModule:delete")
    @ResourceScan("删除资源模块操作")
    public R delete(String ids) {
        long[] idArray = ArrayConverts.toLongArray(ids.split(","));
        List<Long> longs = Arrays.stream(idArray).boxed().collect(Collectors.toList());
        return Controllers.builder("删除资源模块").execLight(longs, idLongs -> resourceModuleRepository.delete(longs));
    }

    /**
     * 查询出资源模块树
     *
     * @return list zTree
     */
    @RequestMapping("resModuleTree")
    @RequiresPermissions("base:resModule:resModuleTree")
    @ResourceScan("查询出资源模块树")
    public List<Ztree> resourceModulesTree(String systemModuleCode) {
        if (StringUtils.isEmpty(systemModuleCode)) {
            return PublicServices.model2Ztree(resourceModuleRepository.findByEnabled(true));
        } else {
            return PublicServices.model2Ztree(resourceModuleRepository.findBySystemModuleCodeAndEnabled(systemModuleCode, true));
        }
    }


}
