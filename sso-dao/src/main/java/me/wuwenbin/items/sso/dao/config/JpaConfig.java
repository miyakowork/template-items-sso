package me.wuwenbin.items.sso.dao.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import me.wuwenbin.modules.jpa.factory.DaoFactory;
import me.wuwenbin.modules.jpa.factory.business.DataSourceX;
import me.wuwenbin.modules.jpa.factory.business.DbType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Template JPA相关配置
 * created by Wuwenbin on 2017/12/22 at 9:32
 *
 * @author wuwenbin
 */
@Configuration
public class JpaConfig {

    @Bean
    public DruidDataSource dataSourceOne() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public DataSourceX dataSourceX(DruidDataSource dataSource) {
        DataSourceX dataSourceX = new DataSourceX();
        dataSourceX.setDataSource(dataSource);
        dataSourceX.setInitDbType(DbType.Mysql);
        return dataSourceX;
    }

    @Bean
    public DaoFactory daoFactory(DataSourceX dataSourceX) {
        DaoFactory daoFactory = new DaoFactory();
        Map<String, DataSourceX> multiDao = new ConcurrentHashMap<>(2);
        String defaultDao = "sso_default_dao";
        multiDao.put(defaultDao, dataSourceX);
        daoFactory.setDataSourceMap(multiDao);
        daoFactory.setDefaultDao(dataSourceX);
        return daoFactory;
    }
}
