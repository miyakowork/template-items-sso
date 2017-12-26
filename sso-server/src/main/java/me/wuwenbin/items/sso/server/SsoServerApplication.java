package me.wuwenbin.items.sso.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wuwenbin
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.alibaba.druid", "me.wuwenbin"})
public class SsoServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoServerApplication.class, args);
    }
}
