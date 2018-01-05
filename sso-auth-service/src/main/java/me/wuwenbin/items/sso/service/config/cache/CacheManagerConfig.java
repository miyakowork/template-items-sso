package me.wuwenbin.items.sso.service.config.cache;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * created by Wuwenbin on 2017/9/27 at 11:30
 *
 * @author wuwenbin
 */
@Configuration
@EnableCaching
public class CacheManagerConfig {

    @Bean("ehCacheManager")
    public EhCacheManager shiroEhCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache.xml");
        return ehCacheManager;
    }

    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    @Bean
    public EhCacheCacheManager cacheManager(EhCacheManagerFactoryBean ehCacheManagerFactoryBean) {
        return new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactory() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache/ehcache.xml"));
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

}
