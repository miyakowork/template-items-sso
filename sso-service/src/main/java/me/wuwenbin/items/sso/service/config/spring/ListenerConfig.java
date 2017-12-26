package me.wuwenbin.items.sso.service.config.spring;

import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

/**
 * 配置Listener，使SpringBoot能够直接注入原生request
 *
 * @author Wuwenbin
 * @date 2017/7/12
 */
@Configuration
public class ListenerConfig {

    @Bean
    public ServletListenerRegistrationBean<RequestContextListener> servletListenerRegistrationBean() {
        ServletListenerRegistrationBean<RequestContextListener> servletListenerRegistrationBean = new ServletListenerRegistrationBean<>();
        servletListenerRegistrationBean.setListener(new RequestContextListener());
        return servletListenerRegistrationBean;
    }

}
