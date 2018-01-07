package me.wuwenbin.items.sso.eurekaclient.context;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;


/**
 * created by Wuwenbin on 2018/1/4 at 22:40
 *
 * @author wuwenbin
 */
@Component
public final class ClientRepository implements ApplicationContextAware {

    /**
     * 用来存储一些全局变量的容器
     */
    private static WebApplicationContext webApplicationContext;


    public static void put(String key, String accessToken) {
        webApplicationContext.getServletContext().setAttribute(key, accessToken);
    }

    public static String get(String key) {
        return (String) webApplicationContext.getServletContext().getAttribute(key);
    }

    public static void delete(String key) {
        webApplicationContext.getServletContext().removeAttribute(key);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        webApplicationContext = (WebApplicationContext) applicationContext;
    }
}
