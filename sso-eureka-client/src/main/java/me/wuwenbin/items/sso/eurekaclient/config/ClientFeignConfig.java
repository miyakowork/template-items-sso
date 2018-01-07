package me.wuwenbin.items.sso.eurekaclient.config;

import feign.Retryer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * created by Wuwenbin on 2018/1/5 at 14:15
 *
 * @author wuwenbin
 */
@Configuration
public class ClientFeignConfig {

    private final ClientSettings client;

    @Autowired
    public ClientFeignConfig(ClientSettings client) {
        this.client = client;
    }

    /**
     * 默认重试间隔为100ms，最大重试时间为1s,重试次数为10次。
     * 设置此Bean防止短时间未访问到结果就直接报SocketTimeout错
     *
     * @return
     */
    @Bean
    public Retryer feignRetry() {
        return new Retryer.Default(client.getPeriod(), client.getMaxPeriod(), client.getMaxAttempts());
    }

//    @Bean
//    public Retryer retryer() {
//        return Retryer.NEVER_RETRY;
//    }
}
