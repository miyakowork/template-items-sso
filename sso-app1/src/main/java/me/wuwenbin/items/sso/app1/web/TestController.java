package me.wuwenbin.items.sso.app1.web;

import me.wuwenbin.items.sso.eurekaclient.config.ClientSettings;
import me.wuwenbin.items.sso.eurekaclient.context.ClientRepository;
import me.wuwenbin.items.sso.eurekaclient.cookie.ClientCookieUtils;
import me.wuwenbin.items.sso.eurekaclient.service.ClientService;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.security.Encrypt;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * created by Wuwenbin on 2018/1/1 at 23:02
 *
 * @author wuwenbin
 */
@RestController
public class TestController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientSettings clientSettings;

    @RequestMapping("hello")
    public String helloWorld() {
        return "hello world!";
    }

    @RequestMapping("testAjax")
    @RequiresPermissions("app1:authcUrl")
    public String testAjax(String msg) {
        return msg;
    }

    @RequestMapping("/authcUrl")
    @RequiresPermissions("app1:authcUrl")
    public String authcUrl() {
        return "这是要验证权限的，能看到此条信息说明你已经验证过权限了";
    }

    @RequestMapping("/doLogin")
    public R doLogin(HttpServletRequest request, String userName, String userPass) {
        R r = clientService.doLogin(userName, Encrypt.digest.md5Hex(userPass), clientSettings.getSystemCode());
        if (Integer.valueOf(r.get(R.CODE).toString()) == R.SUCCESS) {
            ClientRepository.put(ClientCookieUtils.getCookie(request, clientSettings.getSessionIdCookie()).getValue(), r.get("access_token").toString());
        }
        return r;
    }

}
