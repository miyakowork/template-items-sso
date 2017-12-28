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
@RequestMapping("oauth2/log")
public class UserLoginLogController extends BaseController {

    @RequestMapping
    @RequiresPermissions("base:log:list")
    @ResourceScan("用户登录日志列表展示页面")
    public String list() {
        return "router/log/list";
    }
}
