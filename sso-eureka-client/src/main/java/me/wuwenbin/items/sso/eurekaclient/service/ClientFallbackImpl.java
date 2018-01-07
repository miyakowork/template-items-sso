package me.wuwenbin.items.sso.eurekaclient.service;

import me.wuwenbin.items.sso.eurekaclient.context.ClientCode;
import me.wuwenbin.modules.utils.http.R;
import org.apache.shiro.authz.annotation.Logical;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * created by Wuwenbin on 2018/1/4 at 17:06
 *
 * @author wuwenbin
 */
@Component("clientFallback")
public class ClientFallbackImpl implements ClientService {

    @Override
    public R doLogin(String userName, String userPass, String systemCode) {
        return R.error("登录失败，登录接口调用异常！");
    }

    @Override
    public R isPermissionAuthenticated(String[] permissionMarks, String accessToken, Logical logical) {
        return R.custom(ClientCode.CALL_FAILURE, "调用验权接口失败！");
    }

    @Override
    public R isRoleAuthenticated(String[] roleNames, @RequestParam("systemCode") String systemCode, String accessToken, Logical logical) {
        return R.custom(ClientCode.CALL_FAILURE, "调用验权接口失败！");
    }
}
