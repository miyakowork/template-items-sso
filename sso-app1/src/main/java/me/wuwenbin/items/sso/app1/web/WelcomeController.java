package me.wuwenbin.items.sso.app1.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * created by Wuwenbin on 2018/1/4 at 20:37
 *
 * @author wuwenbin
 */
@Controller
public class WelcomeController {

    @RequestMapping("welcome")
    public String helloWorld() {
        return "welcome";
    }

    @RequestMapping("login")
    public String login() {
        return "login";
    }
}
