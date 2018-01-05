package me.wuwenbin.items.sso.eurekaclient.config;

import me.wuwenbin.items.sso.eurekaclient.interceptor.ClientInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * created by Wuwenbin on 2018/1/4 at 23:16
 *
 * @author wuwenbin
 */
@Configuration
public class WebInterceptorConfig extends WebMvcConfigurerAdapter {

    private final ClientInterceptor clientInterceptor;
    @Value("${template.client.filter-auth-url}")
    private String authUrl;
    @Value("${template.client.filter-none-url}")
    private String noneUrl;

    @Autowired
    public WebInterceptorConfig(ClientInterceptor clientInterceptor) {
        this.clientInterceptor = clientInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clientInterceptor).addPathPatterns(authUrl).excludePathPatterns(noneUrl);
        super.addInterceptors(registry);
    }
}
