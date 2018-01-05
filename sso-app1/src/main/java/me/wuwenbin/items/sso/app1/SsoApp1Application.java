package me.wuwenbin.items.sso.app1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * @author wuwenbin
 */
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "me.wuwenbin")
@SpringBootApplication(scanBasePackages = "me.wuwenbin")
public class SsoApp1Application {

    public static void main(String[] args) {
        SpringApplication.run(SsoApp1Application.class, args);
    }

}
