package me.wuwenbin.items.sso.server.web.page;

import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by Wuwenbin on 2017/9/8 at 14:18
 *
 * @author wuwenbin
 */
@Controller
@RequestMapping("oauth2/operationPrivilegeType")
public class OperationPrivilegeTypeController extends BaseController {

    @RequestMapping
    @RequiresPermissions("base:operationPrivilegeType:list")
    @ResourceScan("操作级权限类型列表页面")
    public String list() {
        return "router/operation_privilege_type/list";
    }
}
