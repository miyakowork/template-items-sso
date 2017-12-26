package me.wuwenbin.items.sso.server.web.page;

import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by Wuwenbin on 2017/8/27 at 10:21
 *
 * @author wuwenbin
 */
@Controller
@RequestMapping("oauth2/menu")
public class MenuController extends BaseController {

    @RequestMapping
    @RequiresPermissions("base:menu:list")
    @ResourceScan("菜单树列表表格页面展示")
    public String list() {
        return "router/menu/list";
    }

    @RequestMapping("parentTree")
    @RequiresPermissions("base:menu:parentTree")
    @ResourceScan("选择父级菜单的弹出框页面")
    public String parentTree() {
        return "router/menu/parentTree";
    }

    @RequestMapping("resourceSelect")
    @RequiresPermissions("base:menu:resourceSelect")
    @ResourceScan("勾选菜单的对应的资源弹出框页面")
    public String resourceSelect() {
        return "router/menu/resourceSelect";
    }
}
