package me.wuwenbin.items.sso.eurekaclient.service;

import me.wuwenbin.modules.utils.http.R;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

/**
 * created by Wuwenbin on 2018/1/4 at 16:46
 *
 * @author wuwenbin
 */
@FeignClient(value = "sso-auth-server", fallback = PermissionFallbackServiceImpl.class)
public interface PermissionService {

    /**
     * 第三方登录API
     *
     * @param userName
     * @param userPass
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    R doLogin(@RequestParam("userName") String userName, @RequestParam("userPass") String userPass);

    /**
     * 查询角色名集合
     *
     * @param userId
     * @param systemCode
     * @return
     */
    @RequestMapping(value = "/oauth2/remote/api/findRoleNamesBySystemCode", method = RequestMethod.POST)
    Set<String> findRoles(@RequestParam("userId") long userId, @RequestParam("systemCode") String systemCode);

    /**
     * 查询权限名集合
     *
     * @param userId
     * @param systemCode
     * @return
     */
    @RequestMapping(value = "/oauth2/remote/api/findPermissionNamesBySystemCode", method = RequestMethod.POST)
    Set<String> findPermissions(@RequestParam("userId") long userId, @RequestParam("systemCode") String systemCode);


}
