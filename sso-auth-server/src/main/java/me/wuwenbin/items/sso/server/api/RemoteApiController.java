package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.repository.ResourceRepository;
import me.wuwenbin.items.sso.dao.repository.RoleRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * created by Wuwenbin on 2018/1/4 at 17:10
 *
 * @author wuwenbin
 */
@RestController
@RequestMapping("oauth2/remote/api")
public class RemoteApiController extends BaseController {

    @Resource
    private RoleRepository roleRepository;
    @Resource
    private ResourceRepository resourceRepository;

    @RequestMapping("findRoleNamesBySystemCode")
    @RequiresPermissions("base:role:findRoleNamesBySystemCode")
    @ResourceScan("查找出当前系统中用户所含有的所有角色名")
    public Set<String> findRoleNamesBySystemCode(String systemCode, Long userId) {
        return new HashSet<>(roleRepository.findRoleNamesByUserIdAndSystemCode(userId, systemCode));
    }

    @RequestMapping("findPermissionNamesBySystemCode")
    @RequiresPermissions("base:role:findPermissionNamesBySystemCode")
    @ResourceScan("查找出当前系统中用户所含有的所有权限名")
    public Set<String> findPermissionNamesBySystemCode(String systemCode, Long userId) {
        return new HashSet<>(resourceRepository.findPermissionByUserIdAndSystemCode(userId, systemCode));
    }

}
