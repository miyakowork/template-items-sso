package me.wuwenbin.items.sso.server.web;

import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 首页控制类
 *
 * @author Wuwenbin
 * @date 2017/7/11
 */
@Controller
public class IndexController extends BaseController {

    private static final String TO_ROLE_ID = "roleId";

    @Resource
    private UserRepository userRepository;

    /**
     * 请求系统访问页
     *
     * @return
     */
    @RequestMapping({"", "/"})
    public String index() {
        //切换角色id
        if (!StringUtils.isEmpty(getRequest().getParameter(TO_ROLE_ID))) {
            int toRoleId = getParameter(Integer.class, TO_ROLE_ID);
            User user = UserUtils.getLoginUser();
            try {
                userRepository.updateDefaultRoleIdById(toRoleId, user.getId());
            } catch (Exception ignore) {
            }
        }
        //防止路由地址无限循环请求
        if (isRouter()) {
            return "router/dashboard";
        }
        return "index";
    }

    @RequestMapping({"oauth2", "oauth2/index"})
    public String oauth2Index() {
        return "redirect:/";
    }

    @RequestMapping("oauth2/dashboard")
    public String dashboard() {
        return "router/dashboard";
    }

    @RequestMapping("oauth2/systems")
    public String systems() {
        return "systems";
    }
}
