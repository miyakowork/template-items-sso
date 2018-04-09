package me.wuwenbin.items.sso.dao.config;

import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import me.wuwenbin.modules.repository.registry.RepositoryRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Template Repository的相关配置
 * created by Wuwenbin on 2017/12/22 at 9:35
 *
 * @author wuwenbin
 */
@Configuration
public class RepositoryConfig {

    @Bean
    public static RepositoryRegistry repositoryRegistry() {
        return new RepositoryRegistry("me.wuwenbin");
    }

    @Bean
    public RepositoryFactory repositoryFactory(ApplicationContext applicationContext) {
        RepositoryFactory factory = new RepositoryFactory();
        factory.setApplicationContext(applicationContext);
        return factory;
    }
}
