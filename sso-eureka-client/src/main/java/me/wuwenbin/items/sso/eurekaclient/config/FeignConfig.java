package me.wuwenbin.items.sso.eurekaclient.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by Wuwenbin on 2018/1/5 at 14:15
 *
 * @author wuwenbin
 */
@Configuration
public class FeignConfig {

    /**
     * 重试间隔为100ms，最大重试时间为1s,重试次数为10次。
     *
     * @return
     */
    @Bean
    public Retryer feignRetry() {
        return new Retryer.Default(100, 1000, 10);
    }
}
