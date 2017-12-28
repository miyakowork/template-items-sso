package me.wuwenbin.items.sso.server.web.page;

import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangteng
 * @date 2017/7/19
 */
@Controller
@RequestMapping("oauth2/privilegePage")
public class PrivilegePageController extends BaseController {

    @RequestMapping
    @RequiresPermissions("base:privilegePage:list")
    @ResourceScan("页面级权限页面")
    public String list() {
        return "router/privilege_page/list";
    }

    /**
     * 添加页面级权限资源的时候获取的资源树弹出框
     *
     * @return
     */
    @RequestMapping("resourceSelect")
    @RequiresPermissions("base:privilegePage:resourceSelect")
    @ResourceScan("添加页面级权限资源的时候获取的资源树弹出框")
    public String resourceTree() {
        return "router/privilege_page/resourceSelect";
    }
}
