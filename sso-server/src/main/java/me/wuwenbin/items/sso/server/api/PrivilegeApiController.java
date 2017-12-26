package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.model.Ztree;
import me.wuwenbin.items.sso.service.service.PrivilegeService;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import me.wuwenbin.modules.utils.http.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wuwenbin
 * @date 2017/8/21
 */
@RestController
@RequestMapping("oauth2/privilege/api")
public class PrivilegeApiController extends BaseController {

    @Autowired
    private PrivilegeService privilegeService;

    /**
     * 异步加载权限资源
     *
     * @param resourceModuleId
     * @param roleId
     * @return
     */
    @RequestMapping("getAjaxPrivilegeData")
    @RequiresPermissions("base:privilege:getAjaxPrivilegeData")
    @ResourceScan("获取异步加载权限资源的操作")
    public List<Ztree> getAjaxPrivilegeData(String resourceModuleId, String roleId) {
        return privilegeService.getPrivilegeData(resourceModuleId, roleId);
    }

    /**
     * 分配操作权限
     *
     * @param resourceIds
     * @param roleId
     * @param checked
     * @return
     */
    @RequestMapping("setPrivilege")
    @RequiresPermissions("base:privilege:setPrivilege")
    @ResourceScan("分配操作权限的操作")
    public R setPrivilege(@RequestParam(value = "resourceIds[]") String[] resourceIds, String roleId, boolean checked) {
        String operate = checked ? "分配" : "撤销";
        try {
            privilegeService.setPrivilege(resourceIds, roleId, checked);
            return R.ok(operate + "权限成功！");
        } catch (Exception e) {
            return R.error(operate + "权限失败，失败原因:" + e.getMessage());
        }
    }
}
