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
public final class GlobalRepository implements ApplicationContextAware {

    public static final String ACCESS_TOKEN = GlobalRepository.class.getName().concat("_ACCESS_TOKEN");
    /**
     * 用来存储一些全局变量的容器
     */
    private static WebApplicationContext webApplicationContext;

    /**
     * 默认失效时间：30分钟
     */
    public static final long DEFAULT_EXPIRE = 30 * 60 * 60;

    public static <V> void put(String key, V value, long expire) {
        webApplicationContext.getServletContext().setAttribute(key, new CacheBean<>(key, value, expire));
    }

    public static <V> void put(String key, V value) {
        put(key, value, DEFAULT_EXPIRE);
    }

    public static <V> V get(String key) {
        //noinspection unchecked
        CacheBean<String, V> cacheBean = (CacheBean<String, V>) webApplicationContext.getServletContext().getAttribute(key);
        if (cacheBean == null) {
            return null;
        }
        if (cacheBean.isExpired()) {
            return null;
        }
        return cacheBean.get();
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        webApplicationContext = (WebApplicationContext) applicationContext;
    }
}
