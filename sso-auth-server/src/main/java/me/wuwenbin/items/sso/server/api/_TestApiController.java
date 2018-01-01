package me.wuwenbin.items.sso.server.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by Wuwenbin on 2017/12/30 at 1:04
 */
@RestController
@RequestMapping("test")
public class _TestApiController {

    @RequestMapping("username")
    public String getUsername() {
        return "伍文彬";
    }

}
