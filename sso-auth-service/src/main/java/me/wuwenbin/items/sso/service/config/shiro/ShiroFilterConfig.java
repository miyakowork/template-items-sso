package me.wuwenbin.items.sso.service.config.shiro;

import me.wuwenbin.items.sso.service.config.filter.ForceLogoutFilter;
import me.wuwenbin.items.sso.service.config.filter.MyFormAuthenticationFilter;
import me.wuwenbin.items.sso.service.config.filter.SessionTimeoutFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wuwenbin
 * @date 2017/5/23
 */
@Configuration
public class ShiroFilterConfig {

    @Bean
    public MyFormAuthenticationFilter formAuthenticationFilter() {
        MyFormAuthenticationFilter formAuthenticationFilter = new MyFormAuthenticationFilter();
        formAuthenticationFilter.setUsernameParam("userName");
        formAuthenticationFilter.setPasswordParam("userPass");
        formAuthenticationFilter.setRememberMeParam("rememberMe");
        formAuthenticationFilter.setLoginUrl("/login/");
        return formAuthenticationFilter;
    }


    /**
     * Shiro的Web过滤器
     *
     * @param securityManager
     * @param formAuthenticationFilter
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager,
                                              MyFormAuthenticationFilter formAuthenticationFilter,
                                              ForceLogoutFilter forceLogoutFilter,
                                              SessionTimeoutFilter sessionTimeoutFilter,
                                              UserFilter myUserFilter) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login/");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");
        //此处设置的应该是登录选择系统的界面，因为有可能该用户包含多个可登录的系统的权限
        shiroFilterFactoryBean.setSuccessUrl("/oauth2/systems");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("myAuthc", formAuthenticationFilter);
        filters.put("forceLogout", forceLogoutFilter);
        filters.put("sto", sessionTimeoutFilter);
        filters.put("user", myUserFilter);
        shiroFilterFactoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new HashMap<>();
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/login/router", "anon");
        filterChainDefinitionMap.put("/login/", "anon");
        filterChainDefinitionMap.put("/management/**", "forceLogout,sto,user");
        filterChainDefinitionMap.put("/oauth2/**", "forceLogout,sto,myAuthc");
        filterChainDefinitionMap.put("/", "forceLogout,sto,myAuthc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 保证实现了Shiro内部lifecycle函数的bean执行（shiro生命周期处理器）
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * AOP式方法级权限检查
     *
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
