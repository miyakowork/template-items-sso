package me.wuwenbin.items.sso.eurekaclient;

/**
 * created by Wuwenbin on 2018/1/4 at 23:11
 *
 * @author wuwenbin
 */
public class PermissionIdentity {

    public static boolean isAuthenticated(String accessToken, String permissionMark) {
        return accessToken != null;
    }
}
