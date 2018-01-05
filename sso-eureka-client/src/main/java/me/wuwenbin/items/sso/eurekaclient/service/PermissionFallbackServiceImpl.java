package me.wuwenbin.items.sso.eurekaclient.service;

import me.wuwenbin.modules.utils.http.R;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;

/**
 * created by Wuwenbin on 2018/1/4 at 17:06
 *
 * @author wuwenbin
 */
@Component
public class PermissionFallbackServiceImpl implements PermissionService {

    @Override
    public Set<String> findRoles(long userId, String systemCode) {
        return Collections.emptySet();
    }

    @Override
    public Set<String> findPermissions(long userId, String systemCode) {
        return Collections.emptySet();
    }

    @Override
    public R doLogin(String userName, String userPass) {
        return R.error("登录失败啦！");
    }
}
