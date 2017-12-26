package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.PrivilegePage;
import me.wuwenbin.items.sso.dao.model.pageVo.PrivilegePageVo;
import me.wuwenbin.items.sso.dao.model.queryBo.PrivilegePageBo;
import me.wuwenbin.items.sso.dao.repository.PrivilegePageRepository;
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
 * @author wuwenbin
 * @date 2017/8/19
 */
@RestController
@RequestMapping("oauth2/privilegePage/api")
public class PrivilegePageApiController extends BaseController {

    @Resource
    private PrivilegePageRepository privilegePageRepository;


    @RequestMapping("list")
    @RequiresPermissions("base:privilegePage:list")
    @ResourceScan("获取页面级权限列表的操作")
    public BootstrapTable<PrivilegePageVo> bootstrapTable(Page<PrivilegePageVo> page, PrivilegePageBo privilegePageBo) {
        page = privilegePageRepository.findPagination(page, PrivilegePageVo.class, privilegePageBo);
        return bootstrapTable(page);
    }

    @RequestMapping("add")
    @RequiresPermissions("base:privilegePage:add")
    @ResourceScan("添加页面级权限的操作")
    public R add(PrivilegePage privilegePage) {
        privilegePage.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加页面级权限").exec(() -> privilegePageRepository.save(privilegePage) != null);
    }

    @RequestMapping("edit")
    @RequiresPermissions("base:privilegePage:edit")
    @ResourceScan("编辑页面级权限的操作")
    public R doEdit(PrivilegePage privilegePage) {
        privilegePage.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("修改页面级权限").exec(() -> privilegePageRepository.updateById(privilegePage) == 1);
    }

    @RequestMapping("delete")
    @RequiresPermissions("base:privilegePage:delete")
    @ResourceScan("删除页面级权限的操作")
    public R delete(String ids) {
        long[] idArray = ArrayConverts.toLongArray(ids.split(","));
        List<Long> longs = Arrays.stream(idArray).boxed().collect(Collectors.toList());
        return Controllers.builder("删除操作及权限").execLight(longs, idLongs -> privilegePageRepository.delete(longs));
    }
}
