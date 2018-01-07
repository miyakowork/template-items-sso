package me.wuwenbin.items.sso.eurekaclient.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.TimeUnit;

/**
 * created by Wuwenbin on 2018/1/5 at 17:10
 *
 * @author wuwenbin
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "template.client")
@PropertySource("classpath:template-client.properties")
public class ClientSettings {

    /**
     * 非对称密钥中的私钥
     */
    private String privateKey;
    /**
     * 需要认证权限的URL表达式
     */
    private String authUrl = "/";
    /**
     * 无需认证权限的URL表达式
     */
    private String excludeUrl = "";
    /**
     * app首页
     */
    private String indexUrl;
    /**
     * 系统代码，id
     */
    private String systemCode;
    /**
     * 自定义登录页面Url（不是提交登录action的地址）地址，默认/login
     */
    private String loginUrl = "/login";

    /**
     * 保存于服务器一一对应的cookie的id键值，默认tp-sid
     */
    private String sessionIdCookie = "tp-sid";

    /**
     * sessionIdCookie过期时间
     */
    private int expire = 30 * 60;
    /**
     * Feign Client重试间隔为100ms
     */
    private long period = 100L;
    /**
     * Feign Client最大重试时间为1s
     */
    private long maxPeriod = TimeUnit.SECONDS.toMillis(1L);
    /**
     * Feign Client重试次数为5次。
     */
    private int maxAttempts = 5;

}
