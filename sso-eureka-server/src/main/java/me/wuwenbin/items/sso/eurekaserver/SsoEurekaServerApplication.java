package me.wuwenbin.items.sso.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author wuwenbin
 */
@SpringBootApplication
@EnableEurekaServer
public class SsoEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsoEurekaServerApplication.class, args);
    }
}
