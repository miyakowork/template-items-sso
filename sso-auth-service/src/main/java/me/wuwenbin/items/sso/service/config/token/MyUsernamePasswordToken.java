package me.wuwenbin.items.sso.service.config.token;

import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * created by Wuwenbin on 2018/1/6 at 22:44
 *
 * @author wuwenbin
 */
@Setter
@Getter
public class MyUsernamePasswordToken extends UsernamePasswordToken {

    private String from = "api";

    public MyUsernamePasswordToken(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }
}
