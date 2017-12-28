package me.wuwenbin.items.sso.server.web.page;

import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 部门管理控制层
 *
 * @author wuwenbin
 * @date 2017/7/12
 */
@Controller
@RequestMapping("oauth2/department")
public class DepartmentController extends BaseController {

    @RequestMapping
    @RequiresPermissions("base:department:list")
    @ResourceScan("部门列表展示页面")
    public String list() {
        return "router/department/list";
    }

    @RequestMapping("tree")
    @RequiresPermissions("base:department:tree")
    @ResourceScan("父部门节点的树展示页面")
    public String tree() {
        return "router/department/pIdTree";
    }

}
