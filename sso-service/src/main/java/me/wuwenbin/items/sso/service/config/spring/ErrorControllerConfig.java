package me.wuwenbin.items.sso.service.config.spring;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 全局拦截配置Error
 *
 * @author wuwenbin
 * @date 2017/5/21
 */
@Configuration
public class ErrorControllerConfig {

    /**
     * springBoot内嵌tomcat的拦截异常处理方式
     *
     * @return EmbeddedServletContainerCustomizer
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
            container.addErrorPages(new ErrorPage(HttpStatus.BAD_GATEWAY, "/error/502"));
            container.addErrorPages(new ErrorPage(Exception.class, "/error/exception"));
            container.addErrorPages(new ErrorPage(Throwable.class, "/error/exception"));
        };
    }

}

