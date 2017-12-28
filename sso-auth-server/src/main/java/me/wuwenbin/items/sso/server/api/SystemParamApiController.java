package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.SystemParam;
import me.wuwenbin.items.sso.dao.model.pagevo.SystemParamVo;
import me.wuwenbin.items.sso.dao.model.querybo.SysParamBo;
import me.wuwenbin.items.sso.dao.repository.SystemParamRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
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
 * @author Wuwenbin
 * @date 2017/8/03
 */
@RestController
@RequestMapping("oauth2/systemParam/api")
public class SystemParamApiController extends BaseController {


    @Resource
    private SystemParamRepository systemParamRepository;


    /**
     * 系统参数页面
     *
     * @param sysParamBo sysParamBo
     * @param page       page
     * @return BootstrapTable
     */
    @RequestMapping("list")
    @RequiresPermissions("base:systemParam:list")
    @ResourceScan("获取系统参数列表页面的数据")
    public BootstrapTable<SystemParamVo> sysParams(SysParamBo sysParamBo, Page<SystemParamVo> page) {
        page = systemParamRepository.findPagination(page, SystemParamVo.class, sysParamBo);
        return bootstrapTable(page);
    }

    /**
     * 添加系统参数
     *
     * @param systemParam 添加的对象
     * @return R
     */
    @RequestMapping("add")
    @RequiresPermissions("base:systemParam:add")
    @ResourceScan("添加系统参数")
    public R add(SystemParam systemParam) {
        systemParam.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加系统参数").exec(() -> systemParamRepository.save(systemParam) != null);
    }

    /**
     * 编辑系统参数
     *
     * @param systemParam 编辑的对象
     * @return R
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:systemParam:edit")
    @ResourceScan("编辑系统参数")
    public R edit(SystemParam systemParam) {
        systemParam.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("修改系统参数").exec(() -> systemParamRepository.updateById(systemParam) == 1);
    }

    /**
     * 删除参数
     *
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @RequiresPermissions("base:systemParam:delete")
    @ResourceScan("删除参数")
    public R delete(String ids) {
        long[] idArray = ArrayConverts.toLongArray(ids.split(","));
        List<Long> longs = Arrays.stream(idArray).boxed().collect(Collectors.toList());
        return Controllers.builder("删除操作及权限").execLight(longs, idLongs -> systemParamRepository.delete(longs));
    }

}
