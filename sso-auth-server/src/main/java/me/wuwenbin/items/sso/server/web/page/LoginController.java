package me.wuwenbin.items.sso.server.web.page;

import me.wuwenbin.items.sso.service.support.util.ShiroUtils;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.pagination.util.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录控制类
 *
 * @author Wuwenbin
 * @date 2017/7/11
 */
@Controller
public class LoginController {


    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView login() {
        String userName = UserUtils.getLoginUserName();
        if (StringUtils.isNotEmpty(userName)) {
            return new ModelAndView(new RedirectView("/"));
        }
        return new ModelAndView("login");
    }

    @RequestMapping(value = "login/router", method = RequestMethod.GET)
    public ModelAndView loginRouter() {
        return new ModelAndView("router/login");
    }


    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().invalidate();
        ShiroUtils.logout();
        return new ModelAndView(new RedirectView("login"));
    }

}
