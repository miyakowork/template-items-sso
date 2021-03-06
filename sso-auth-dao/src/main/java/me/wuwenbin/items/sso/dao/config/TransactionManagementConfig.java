package me.wuwenbin.items.sso.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * created by Wuwenbin on 2017/12/28 at 16:03
 *
 * @author wuwenbin
 */
@Configuration
public class TransactionManagementConfig {

    @Bean
    public PlatformTransactionManager transactionManager(DruidDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
