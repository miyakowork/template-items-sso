package me.wuwenbin.items.sso.app1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * created by Wuwenbin on 2017/12/30 at 0:51
 *
 * @author wuwenbin
 */
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getCurrentInfo() {
        return restTemplate.getForObject("http://sso-auth-server/test/username", String.class);
    }
}
