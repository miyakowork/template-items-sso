package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.OperationPrivilegeType;
import me.wuwenbin.items.sso.dao.model.pagevo.OperationPrivilegeTypeVo;
import me.wuwenbin.items.sso.dao.model.querybo.OperationPrivilegeTypeBo;
import me.wuwenbin.items.sso.dao.repository.OptPriTypeRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
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

/**
 * @author zhangteng
 * @date 2017/7/12
 */
@RestController
@RequestMapping("oauth2/operationPrivilegeType/api")
public class OperationPrivilegeTypeApiController extends BaseController {

    @Resource
    private OptPriTypeRepository optPriTypeRepository;


    /**
     * 权限操作类型page
     *
     * @param page
     * @param operationPrivilegeTypeBo
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("base:operationPrivilegeType:list")
    @ResourceScan("获取操作级权限列表页面的数据")
    public BootstrapTable<OperationPrivilegeTypeVo> org(Page<OperationPrivilegeTypeVo> page, OperationPrivilegeTypeBo operationPrivilegeTypeBo) {
        page = optPriTypeRepository.findPagination(page, OperationPrivilegeTypeVo.class, operationPrivilegeTypeBo);
        return bootstrapTable(page);
    }


    /**
     * 添加操作级权限类型
     *
     * @param operationPrivilegeType
     * @return
     */
    @RequestMapping("add")
    @RequiresPermissions("base:operationPrivilegeType:add")
    @ResourceScan("添加操作级权限类型操作")
    public R add(OperationPrivilegeType operationPrivilegeType) {
        operationPrivilegeType.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加操作机权限类型").exec(() -> optPriTypeRepository.save(operationPrivilegeType) != null);
    }

    /**
     * 编辑操作级权限类型对象
     *
     * @param operationPrivilegeType
     * @return
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:operationPrivilegeType:edit")
    @ResourceScan("编辑操作级权限类型对象操作")
    public R edit(OperationPrivilegeType operationPrivilegeType) {
        operationPrivilegeType.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("编辑操作级权限类型").exec(() -> optPriTypeRepository.updateById(operationPrivilegeType) == 1);
    }


    @RequestMapping("find/operationType/enabled")
    @RequiresPermissions("base:operationPrivilegeType:enabled")
    @ResourceScan("查询可用的操作级权限类型的操作")
    public List<OperationPrivilegeType> findEnabledOperationTypes() {
        return optPriTypeRepository.findByEnabled(true);
    }
}
