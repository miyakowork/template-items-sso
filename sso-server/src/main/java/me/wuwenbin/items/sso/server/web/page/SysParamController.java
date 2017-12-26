package me.wuwenbin.items.sso.server.web.page;

import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 资源模块控制层
 * 修改备注：增加控制层的注释
 * <p>
 * modify by wuwenbin
 *
 * @author tuchen
 * @date 2017/7/12
 */
@Controller
@RequestMapping("/oauth2/systemParam")
public class SysParamController extends BaseController {


    @RequestMapping
    @RequiresPermissions("base:systemParam:list")
    @ResourceScan("系统参数列表页面")
    public String list() {
        return "router/system_param/list";
    }


}
