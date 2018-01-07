package me.wuwenbin.items.sso.eurekaclient.config;

import me.wuwenbin.items.sso.eurekaclient.interceptor.ClientIndexInterceptor;
import me.wuwenbin.items.sso.eurekaclient.interceptor.ClientInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * created by Wuwenbin on 2018/1/4 at 23:16
 *
 * @author wuwenbin
 */
@Configuration
public class ClientWebInterceptorConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private ClientSettings client;
    private ApplicationContext applicationContext;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ClientIndexInterceptor(applicationContext))
                .addPathPatterns(client.getIndexUrl());
        registry.addInterceptor(new ClientInterceptor(applicationContext))
                .addPathPatterns(client.getAuthUrl())
                .excludePathPatterns(client.getExcludeUrl());
        super.addInterceptors(registry);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.client = applicationContext.getBean(ClientSettings.class);
    }
}
