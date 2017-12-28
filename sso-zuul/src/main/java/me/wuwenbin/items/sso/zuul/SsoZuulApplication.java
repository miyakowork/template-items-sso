package me.wuwenbin.items.sso.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author wuwenbin
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
public class SsoZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoZuulApplication.class, args);
    }
}
