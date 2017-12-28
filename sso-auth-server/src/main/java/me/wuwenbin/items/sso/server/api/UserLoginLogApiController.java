package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.model.pagevo.LoginLogVo;
import me.wuwenbin.items.sso.dao.model.querybo.LogBo;
import me.wuwenbin.items.sso.dao.repository.UserLoginLogRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.bootstrap.BootstrapTable;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Administrator
 * @date 2017/7/13/013
 */
@RestController
@RequestMapping("oauth2/log/api")
public class UserLoginLogApiController extends BaseController {

    @Resource
    private UserLoginLogRepository loginLogRepository;

    @RequestMapping("list")
    @RequiresPermissions("base:log:list")
    @ResourceScan("获取用户登录日志列表")
    public BootstrapTable<LoginLogVo> list(Page<LoginLogVo> page, LogBo logBo) {
        page = loginLogRepository.findPagination(page, LoginLogVo.class, logBo);
        return bootstrapTable(page);
    }

}
