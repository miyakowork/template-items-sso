package me.wuwenbin.items.sso.eurekaclient.service;

import me.wuwenbin.modules.utils.http.R;
import org.apache.shiro.authz.annotation.Logical;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * created by Wuwenbin on 2018/1/4 at 16:46
 *
 * @author wuwenbin
 */
@FeignClient(value = "sso-auth-server", fallback = ClientFallbackImpl.class)
@Component("clientService")
public interface ClientService {

    /**
     * 第三方登录API
     *
     * @param userName
     * @param userPass
     * @param systemCode
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    R doLogin(@RequestParam("userName") String userName, @RequestParam("userPass") String userPass, @RequestParam("systemCode") String systemCode);


    /**
     * 验证是否可以授权访问
     *
     * @param permissionMarks
     * @param accessToken
     * @param logical
     * @return
     */
    @RequestMapping(value = "/remote/api/authenticate/permissions", method = RequestMethod.POST)
    R isPermissionAuthenticated(@RequestParam("permissionMarks[]") String[] permissionMarks, @RequestParam("accessToken") String accessToken, @RequestParam("logical") Logical logical);

    /**
     * 验证是否可以授权访问
     *
     * @param roleNames
     * @param systemCode
     * @param accessToken
     * @param logical
     * @return
     */
    @RequestMapping(value = "/remote/api/authenticate/roleNames", method = RequestMethod.POST)
    R isRoleAuthenticated(@RequestParam("roleNames[]") String[] roleNames, @RequestParam("systemCode") String systemCode, @RequestParam("accessToken") String accessToken, @RequestParam("logical") Logical logical);

}
