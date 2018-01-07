package me.wuwenbin.items.sso.eurekaclient.context;

/**
 * created by Wuwenbin on 2018/1/5 at 20:13
 */
public interface ClientCode {

    /**
     * 调用授权服务器接口失败
     */
    int CALL_FAILURE = 901;

    /**
     * session过期，需要重新授权/登录来获取access_token
     */
    int SESSION_TIMEOUT = 902;

    /**
     * 验证权限失败，即该用户无权访问鉴权的此url
     */
    int AUTH_FAILURE = 903;

}
