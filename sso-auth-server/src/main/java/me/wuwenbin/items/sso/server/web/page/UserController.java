package me.wuwenbin.items.sso.server.web.page;

import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author wuwenbin
 * @date 2017/8/19/019
 */

@Controller
@RequestMapping("oauth2/user")
public class UserController {


    @RequestMapping
    @RequiresPermissions("base:user:list")
    @ResourceScan("用户列表页面")
    public String list() {
        return "router/user/list";
    }

    @RequestMapping("treeDept")
    @RequiresPermissions("base:user:treeDept")
    @ResourceScan("用户部门选择弹框页面")
    public String deptTree() {
        return "router/user/deptTree";
    }

    @RequestMapping("treeRole")
    @RequiresPermissions("base:user:treeRole")
    @ResourceScan("用户角色选择弹框页面")
    public String roleTree() {
        return "router/user/roleTree";
    }
}
