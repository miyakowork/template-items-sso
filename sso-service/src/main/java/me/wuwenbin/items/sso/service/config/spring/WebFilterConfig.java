package me.wuwenbin.items.sso.service.config.spring;

import me.wuwenbin.items.sso.service.config.filter.MyUserFilter;
import me.wuwenbin.items.sso.service.config.filter.XssFilter;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;

/**
 * Xss过滤拦截
 *
 * @author wuwenbin
 * @date 2017/5/23
 */
@Configuration
public class WebFilterConfig {

    @Bean
    public UserFilter userFilter() {
        return new MyUserFilter();
    }

    /**
     * Shiro在web.xml中的配置
     *
     * @return r
     */
    @Bean
    public FilterRegistrationBean shiroFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false，表示生命周期由SpringApplicationContext管理，设置为true则表示由ServletContainer管理
        registration.addInitParameter("targetFilterLifecycle", "true");
        registration.setAsyncSupported(true);
        registration.setOrder(Integer.MAX_VALUE - 1);
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        return registration;
    }


    /**
     * xss过滤器
     *
     * @return xss
     */
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

}
