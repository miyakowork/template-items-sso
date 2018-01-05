package me.wuwenbin.items.sso.service.config.shiro;

import me.wuwenbin.items.sso.service.config.realm.UserRealm;
import me.wuwenbin.items.sso.service.constant.CacheConsts;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Shiro权限配置
 *
 * @author wuwenbin
 * @date 2017/5/22
 */
@Configuration
public class ShiroConfig implements CacheConsts {


    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * Realm实现
     *
     * @param credentialsMatcher
     * @return
     */
    @Bean
    public UserRealm userRealm(CredentialsMatcher credentialsMatcher, EhCacheManager cacheManager) {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        userRealm.setCacheManager(cacheManager);
        userRealm.setAuthenticationCachingEnabled(true);
        //认证缓存的key
        userRealm.setAuthenticationCacheName(AUTH_CACHE);
        userRealm.setAuthorizationCachingEnabled(true);
        //权限缓存的key
        userRealm.setAuthorizationCacheName(PERMIT_CACHE);
        return userRealm;
    }


    @Bean
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(1800);
        redisManager.setTimeout(timeout);
        redisManager.setPassword(password);
        return redisManager;
    }

    @Bean
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }

    /**
     * 会话管理器。全局会话超时时间，默认30分钟，即如果30分钟内没有访问会话将过期
     *
     * @param sessionDao
     * @return
     */
    @Bean
    public SessionManager sessionManager(
            RedisSessionDAO sessionDao,
            RedisCacheManager cacheManager,
            SessionListener listener) {
        MySessionManager sessionManager = new MySessionManager();
        sessionManager.setSessionDAO(sessionDao);
        sessionManager.setCacheManager(cacheManager);
        sessionManager.setSessionListeners(Collections.singletonList(listener));
        return sessionManager;
    }


    /**
     * 安全管理器
     *
     * @param userRealm
     * @param sessionManager //     * @param cacheManager
     * @param cacheManager
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager(
            UserRealm userRealm,
            SessionManager sessionManager,
            RedisCacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(userRealm);
        securityManager.setSessionManager(sessionManager);
        securityManager.setCacheManager(cacheManager);
        SecurityUtils.setSecurityManager(securityManager);
        return securityManager;
    }


}
