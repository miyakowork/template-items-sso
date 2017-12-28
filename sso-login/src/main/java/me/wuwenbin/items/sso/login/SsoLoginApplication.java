package me.wuwenbin.items.sso.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author wuwenbin
 */
@SpringBootApplication
@EnableEurekaClient
public class SsoLoginApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoLoginApplication.class, args);
    }
}
