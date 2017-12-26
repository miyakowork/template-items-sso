package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.model.pageVo.SystemModuleVo;
import me.wuwenbin.items.sso.dao.model.queryBo.SystemModuleBo;
import me.wuwenbin.items.sso.dao.repository.SystemModuleRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.model.Ztree;
import me.wuwenbin.items.sso.service.service.common.PublicServices;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.bootstrap.BootstrapTable;
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
 * 系统面模块数据处理层
 *
 * @author zhangteng
 * @date 2017/7/14
 */
@RequestMapping("/oauth2/systemModule/api")
@RestController
public class SysModuleApiController extends BaseController {

    @Resource
    private SystemModuleRepository systemModuleRepository;


    /**
     * 系统模块列表
     *
     * @param page           页面对象
     * @param systemModuleBo 页面查询对象
     * @return 页面对象（包含数据、分页、查询等信息）
     */
    @RequestMapping("list")
    @RequiresPermissions("base:systemModule:list")
    @ResourceScan("获取系统模块列表页面的数据")
    public BootstrapTable<SystemModuleVo> list(Page<SystemModuleVo> page, SystemModuleBo systemModuleBo) {
        page = systemModuleRepository.findPagination(page, SystemModuleVo.class, systemModuleBo);
        return bootstrapTable(page);
    }

    /**
     * 系统模块的添加处理
     *
     * @param systemModule 添加的系统模块对象
     * @return 添加处理情况
     */
    @RequestMapping("add")
    @RequiresPermissions("base:systemModule:add")
    @ResourceScan("系统模块的添加处理")
    public R add(SystemModule systemModule) {
        systemModule.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加系统模块")
                .exec(() -> systemModuleRepository.countBySystemCode(systemModule.getSystemCode()) == 0,
                        () -> systemModuleRepository.save(systemModule) != null,
                        () -> R.error("系统模块代码已存在"));
    }

    /**
     * 修改系统模块的操作
     *
     * @param systemModule 修改的系统模块对象
     * @return 修改处理结果
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:systemModule:edit")
    @ResourceScan("修改系统模块的操作")
    public R doEdit(SystemModule systemModule) {
        systemModule.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("修改系统模块").exec(() -> systemModuleRepository.updateById(systemModule) == 1);
    }


    /**
     * 删除系统模块处理操作
     *
     * @param ids 删除模块对象的id集合字符串
     * @return 删除的处理结果
     */
    @RequestMapping("delete")
    @RequiresPermissions("base:systemModule:delete")
    @ResourceScan("删除系统模块处理操作")
    public R delete(String ids) {
        long[] idArray = ArrayConverts.toLongArray(ids.split(","));
        List<Long> longs = Arrays.stream(idArray).boxed().collect(Collectors.toList());
        return Controllers.builder("删除操作及权限").execLight(longs, idLongs -> systemModuleRepository.delete(longs));
    }

    /**
     * @return 获取所有可用的系统模块
     */
    @RequestMapping("find/modules/enabled")
    @RequiresPermissions("base:systemModule:enabled")
    @ResourceScan("获取所有可用的系统模块")
    public List<SystemModule> findModulesEnabled() {
        return systemModuleRepository.findByEnabled(true);
    }

    /**
     * 获取系统模块的zTree树，无异步加载
     *
     * @return List<ZTreeBO>
     */
    @RequestMapping("/find/modules/enabled/moduleTree")
    @RequiresPermissions("base:systemModule:moduleTree")
    @ResourceScan("获取系统模块的zTree树，无异步加载")
    public List<Ztree> findModulesTreeEnabled() {
        return PublicServices.model2Ztree(systemModuleRepository.findByEnabled(true));
    }

}
