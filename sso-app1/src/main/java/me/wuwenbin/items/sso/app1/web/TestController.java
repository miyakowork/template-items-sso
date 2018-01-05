package me.wuwenbin.items.sso.app1.web;

import me.wuwenbin.items.sso.eurekaclient.context.GlobalRepository;
import me.wuwenbin.items.sso.eurekaclient.service.PermissionService;
import me.wuwenbin.modules.utils.http.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Wuwenbin on 2018/1/1 at 23:02
 *
 * @author wuwenbin
 */
@RestController
public class TestController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping("hello")
    public String helloWorld() {
        return "hello world!";
    }

    @RequestMapping("/authcUrl")
    @RequiresPermissions("aap1:authcUrl")
    public String authcUrl() {
        return "这是要验证权限的，能看到此条信息说明你已经验证过权限了";
    }

    @RequestMapping("/doLogin")
    public R doLogin(String userName, String userPass) {
        R r = permissionService.doLogin(userName, Base64Utils.encodeToString(userPass.getBytes()));
        if (Integer.valueOf(r.get(R.CODE).toString()) == R.SUCCESS) {
            GlobalRepository.put(GlobalRepository.ACCESS_TOKEN, r.get("access_token"));
        }
        return r;
    }
}
