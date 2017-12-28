package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.PrivilegeOperation;
import me.wuwenbin.items.sso.dao.model.pagevo.PrivilegeOperationVo;
import me.wuwenbin.items.sso.dao.model.querybo.PrivilegeOperationBo;
import me.wuwenbin.items.sso.dao.repository.PrivilegeOperationRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.layui.LayTable;
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
 * created by Wuwenbin on 2017/8/22 at 13:42
 *
 * @author wuwenbin
 */
@RestController
@RequestMapping("oauth2/privilegeOperation/api")
public class PrivilegeOperationApiController extends BaseController {

    @Resource
    private PrivilegeOperationRepository privilegeOperationRepository;


    @RequestMapping("list")
    @RequiresPermissions("base:privilegeOperation:list")
    @ResourceScan("获取操作级权限页面数据")
    public LayTable<PrivilegeOperationVo> privilegeOperationPage(Page<PrivilegeOperationVo> page, PrivilegeOperationBo privilegeOperationBO) {
        page = privilegeOperationRepository.findPagination(page, PrivilegeOperationVo.class, privilegeOperationBO);
        return layTable(page);
    }

    @RequestMapping("add")
    @RequiresPermissions("base:privilegeOperation:add")
    @ResourceScan("添加操作级权限操作")
    public R add(PrivilegeOperation privilegeOperation) {
        privilegeOperation.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加操作机权限").exec(() -> privilegeOperationRepository.save(privilegeOperation) != null);
    }

    @RequestMapping("edit")
    @RequiresPermissions("base:privilegeOperation:edit")
    @ResourceScan("编辑操作级权限操作")
    public R edit(Long id, String operationName) {
        return Controllers.builder("修改操作机权限名称").exec(() -> privilegeOperationRepository.updateOperationNameById(operationName, id) == 1);
    }

    @RequestMapping("delete")
    @RequiresPermissions("base:privilegeOperation:delete")
    @ResourceScan("删除操作级权限操作")
    public R delete(String ids) {
        long[] idArray = ArrayConverts.toLongArray(ids.split(","));
        List<Long> longs = Arrays.stream(idArray).boxed().collect(Collectors.toList());
        return Controllers.builder("删除操作及权限").execLight(longs, idLongs -> privilegeOperationRepository.delete(longs));
    }
}
