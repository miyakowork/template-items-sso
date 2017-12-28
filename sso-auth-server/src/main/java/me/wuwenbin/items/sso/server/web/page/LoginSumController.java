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
@RequestMapping("oauth2/loginsum")
public class LoginSumController extends BaseController {

    @RequestMapping
    @RequiresPermissions("base:loginsum:list")
    @ResourceScan("用户登录图标展示页面")
    public String list() {
        return "router/loginsum/list";
    }
}
