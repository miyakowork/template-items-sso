package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.Resource;
import me.wuwenbin.items.sso.dao.model.pagevo.ResourceVo;
import me.wuwenbin.items.sso.dao.model.querybo.ResourceBo;
import me.wuwenbin.items.sso.dao.model.querybo.ResourceLayBo;
import me.wuwenbin.items.sso.dao.repository.ResourceRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.service.ResourceService;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.bootstrap.BootstrapTable;
import me.wuwenbin.modules.pagination.model.layui.LayTable;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import me.wuwenbin.modules.utils.bean.ArrayConverts;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.web.Controllers;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Wuwenbin
 * @date 2017/8/3
 */
@RestController
@RequestMapping("oauth2/resource/api")
public class ResourceApiController extends BaseController {

    @Autowired
    private ResourceService resourceService;
    @javax.annotation.Resource
    private ResourceRepository resourceRepository;


    /**
     * 资源列表页面
     *
     * @param page
     * @param resourceBo
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("base:resource:list")
    @ResourceScan("获取资源列表页面数据")
    public BootstrapTable<ResourceVo> resources(Page<ResourceVo> page, ResourceBo resourceBo) {
        page = resourceRepository.findPagination(page, ResourceVo.class, resourceBo);
        return bootstrapTable(page);
    }

    /**
     * 查询未分配的资源列表
     *
     * @param page
     * @param resourceBO
     * @return
     */
    @RequestMapping("selectResources")
    @RequiresPermissions("base:resource:selectResources")
    @ResourceScan("获取资源列表弹框选择页面的数据")
    public LayTable<ResourceVo> selectResources(Page<ResourceVo> page, ResourceLayBo resourceBO) {
        page = resourceService.findResourceSelectPage(resourceBO, page);
        return layTable(page);
    }

    /**
     * 添加新资源
     *
     * @param resource
     * @return
     */
    @RequestMapping("add")
    @RequiresPermissions("base:resource:add")
    @ResourceScan("添加新资源操作")
    public R add(Resource resource) {
        resource.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加资源").exec(() -> resourceRepository.save(resource) != null);
    }

    /**
     * 编辑资源信息
     *
     * @param resource
     * @return
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:resource:edit")
    @ResourceScan("编辑资源信息操作")
    public R edit(Resource resource) {
        resource.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("编辑资源").exec(() -> resourceRepository.updateById(resource) == 1);
    }


    /**
     * 删除资源
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @RequestMapping("delete")
    @RequiresPermissions("base:resource:delete")
    @ResourceScan("删除资源信息操作")
    public R delete(String ids) {
        long[] idArray = ArrayConverts.toLongArray(ids.split(","));
        List<Long> longs = Arrays.stream(idArray).boxed().collect(Collectors.toList());
        return Controllers.builder("删除操作及权限").execLight(longs, idLongs -> resourceRepository.delete(longs));
    }


}
