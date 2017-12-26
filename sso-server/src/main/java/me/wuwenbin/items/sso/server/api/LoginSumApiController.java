package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.model.LoginSumBo;
import me.wuwenbin.items.sso.dao.model.pageVo.LoginSumVo;
import me.wuwenbin.items.sso.service.service.LoginSumService;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuwenbin
 * @date 2017/7/19
 */
@RestController
@RequestMapping("/oauth2/loginsum/api")
public class LoginSumApiController {

    @Autowired
    private LoginSumService loginSumService;

    @RequestMapping("charts")
    @RequiresPermissions("base:loginsum:charts")
    @ResourceScan("获取用户登录图表数据")
    public LoginSumVo getData(LoginSumBo loginSumBO) {
        return loginSumService.getData(loginSumBO);
    }

}
