package me.wuwenbin.items.sso.eurekaclient.cookie;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 当前用户访问需要创建一个cookie来和服务器诶之session的一对一的关系
 * 这样app服务器才能识别当前是哪个用户访问，并验证用户
 * created by Wuwenbin on 2018/1/5 at 18:02
 *
 * @author wuwenbin
 */
public class ClientCookieUtils {

    public static Cookie getCookie(String name) {
        Map<String, Cookie> cookieMap = readCurrentRequestCookieMap();
        return cookieMap.getOrDefault(name, null);
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = readCurrentRequestCookieMap(request);
        return cookieMap.getOrDefault(name, null);
    }

    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String value, int time) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(time);
        response.addCookie(cookie);
        return response;
    }

    public static HttpServletResponse setCookie(HttpServletResponse response, String name, String domain, String value, int time) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(time);
        response.addCookie(cookie);
        return response;
    }

    public static void deleteCookie(HttpServletResponse response, String name) {
        Map<String, Cookie> cookieMap = readCurrentRequestCookieMap();
        loopDelCookieMap(response, name, cookieMap);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Map<String, Cookie> cookieMap = readCurrentRequestCookieMap(request);
        loopDelCookieMap(response, name, cookieMap);
    }

    private static void loopDelCookieMap(HttpServletResponse response, String name, Map<String, Cookie> cookieMap) {
        cookieMap.forEach((k, v) -> {
            if (k.equals(name)) {
                Cookie cookie = cookieMap.get(k);
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        });
    }

    private static Map<String, Cookie> readCurrentRequestCookieMap() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if (null != cookies) {
            Arrays.stream(cookies).forEach(c -> cookieMap.put(c.getName(), c));
        }
        return cookieMap;
    }

    private static Map<String, Cookie> readCurrentRequestCookieMap(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Map<String, Cookie> cookieMap = new HashMap<>();
        if (null != cookies) {
            Arrays.stream(cookies).forEach(c -> cookieMap.put(c.getName(), c));
        }
        return cookieMap;
    }
}
