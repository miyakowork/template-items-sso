package me.wuwenbin.items.sso.app1.web;

import me.wuwenbin.items.sso.app1.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Wuwenbin on 2017/12/30 at 0:48
 *
 * @author wuwenbin
 */
@RestController
@RequestMapping("/app1")
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping
    public Object get(String name) {
        return name.concat(" get from auth server").concat(helloService.getCurrentInfo());
    }
}
