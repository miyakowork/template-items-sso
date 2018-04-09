package me.wuwenbin.items.sso.server.web;

import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.repository.SystemModuleRepository;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.utils.security.Encrypt;
import me.wuwenbin.modules.utils.security.asymmetric.KeyType;
import me.wuwenbin.modules.utils.security.asymmetric.RSA;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private SystemModuleRepository systemModuleRepository;

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
    public String systems(Model model) {
        //noinspection unchecked
        List<SystemModule> systemModules = (List<SystemModule>) SecurityUtils.getSubject().getSession().getAttribute("systemModules");
        model.addAttribute("systems", systemModules);
        return "systems";
    }

    @RequestMapping("/redirectTo")
    public String redirectTo(String systemCode) {
        if (!StringUtils.isEmpty(systemCode)) {
            String systemIndex = systemModuleRepository.findIndexUrlBySystemCode(systemCode);
            String redirectUrl = "redirect:" + systemIndex + "?action=redirect2Module&f=uls";
            if (redirectUrl.contains(" ?")) {
                redirectUrl = redirectUrl.concat("&access_token=");
            } else {
                redirectUrl = redirectUrl.concat("?access_token=");
            }
            String publicKey = systemModuleRepository.findPublicKeyBySystemCode(systemCode);
            String accessToken = SecurityUtils.getSubject().getSession().getId().toString();
            RSA rsa = new RSA(null, Encrypt.base64.decode(publicKey));
            String encryptAccessToken = rsa.encryptStr(accessToken, KeyType.PublicKey);
            return redirectUrl.concat(encryptAccessToken);
        }
        return "systems";
    }

}
