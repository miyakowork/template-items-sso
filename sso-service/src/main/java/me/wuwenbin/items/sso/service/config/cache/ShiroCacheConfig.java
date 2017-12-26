package me.wuwenbin.items.sso.service.config.cache;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by Wuwenbin on 2017/9/27 at 11:30
 * @author wuwenbin
 */
@Configuration
public class ShiroCacheConfig {

    @Bean
    public EhCacheManager shiroEhCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache.xml");
        return ehCacheManager;
    }
}
