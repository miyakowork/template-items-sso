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
@RequestMapping("oauth2/resModule")
public class ResModuleController extends BaseController {

    @RequestMapping
    @RequiresPermissions("base:resModule:list")
    @ResourceScan("资源模块列表页面")
    public String list() {
        return "router/res_module/list";
    }
}
