package me.wuwenbin.items.sso.service.constant;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * Shiro静态常量
 * Created by wuwenbin on 2017/4/25.
 */
public interface ShiroConsts {

    /**
     * 用户登录时，系统模块对应的key的键值
     */
    String LOGIN_SYSTEM_MODULE_KEY = "login.system.module.key";

    /**
     * 获取当前session中的用户名key
     */
    String SESSION_USERNAME_KEY = "session.username.key";

    /**
     * 存取user的key
     */
    String SESSION_USER_KEY = "session.user.key";

    /**
     * 获取登录失败时候的错误异常的key
     */
    String LOGIN_FAILURE = FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME;

    /**
     * 获取强制退出的标识的key
     */
    String SESSION_FORCE_LOGOUT_KEY = "session.force.logout";

    /**
     * 获取登录前的url，准备登陆成功之后跳转的url的key
     */
    String BEFORE_LOGIN_SUCCESS_URL = "before.login.success.url";

    /**
     * 登录URL页面地址
     */
    String LOGIN_URL = "/login/";

    /**
     * 登录路由地址
     */
    String LOGIN_ROUTER = "/login/router";

    /**
     * favicon
     */
    String FAVICON = "/favicon.ico";


}

