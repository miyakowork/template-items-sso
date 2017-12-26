package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.Role;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.model.pageVo.UserVo;
import me.wuwenbin.items.sso.dao.model.queryBo.UserBo;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.service.RoleService;
import me.wuwenbin.items.sso.service.service.UserService;
import me.wuwenbin.items.sso.service.support.util.ShiroUtils;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.bootstrap.BootstrapTable;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.web.Controllers;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @author wuwenbin
 * @date 2017/8/8/
 */
@RestController
@RequestMapping("oauth2/user/api")
public class UserApiController extends BaseController {


    @Autowired
    private UserService userService;
    @Resource
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;


    /**
     * 获取当前登录用户登录信息
     *
     * @return
     */
    @RequestMapping("info")
    @RequiresPermissions("base:user:info")
    @ResourceScan("获取当前登录用户登录信息")
    public User getCurrentUserInfo() {
        Object username = ShiroUtils.getSubject().getPrincipal();
        return username == null ? null : userRepository.findByUsername(username.toString());
    }

    /**
     * 显示数据
     *
     * @param page
     * @param userBo
     * @return
     */
    @RequestMapping("list")
    @RequiresPermissions("base:user:list")
    @ResourceScan("获取用户列表页面数据")
    public BootstrapTable<UserVo> list(Page<UserVo> page, UserBo userBo) {
        page = userRepository.findPagination(page, UserVo.class, userBo);
        return bootstrapTable(page);
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @RequestMapping("add")
    @RequiresPermissions("base:user:add")
    @ResourceScan("添加用户")
    public R add(User user) {
        user.preInsert(UserUtils.getLoginUser()::getId);
        return Controllers.builder("添加用户")
                .exec(() -> userRepository.findByUsername(user.getUsername()) == null,
                        () -> userService.addNewUser(user) > 0,
                        () -> R.error("此账号已存在！"));
    }

    /**
     * 编辑用户
     *
     * @param user
     * @return
     */
    @RequestMapping("edit")
    @RequiresPermissions("base:user:edit")
    @ResourceScan("编辑用户")
    public R doEdit(User user) {
        user.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("修改用户").exec(() -> userRepository.editUserInfo(user) > 0);
    }


    /**
     * 修改用户密码
     *
     * @param username
     * @param newPwd
     * @return
     */
    @RequestMapping("editPwd")
    @RequiresPermissions("base:user:editPwd")
    @ResourceScan("修改用户密码")
    public R doEditPwd(String username, String newPwd) {
        User user = userRepository.findByUsername(username);
        user.preUpdate(UserUtils.getLoginUser()::getId);
        return Controllers.builder("修改用户密码")
                .exec(() -> true,
                        () -> userService.changePasswordByUser(user, newPwd) == 1,
                        () -> R.error("用户不存在，修改失败！"));
    }


    /**
     * 禁用用户
     *
     * @param ids
     * @return
     */
    @RequestMapping("delete")
    @RequiresPermissions("base:user:delete")
    @ResourceScan("禁用用户")
    public R delete(String ids) {
        return Controllers.builder("修改用户状态").exec(() -> userRepository.updateUserStatus(ids.split(",")) > 0);
    }

    /**
     * 查找当前用户拥有的所有角色
     *
     * @return
     */
    @RequestMapping("findCurrentUserRoles")
    @RequiresPermissions("base:user:findCurrentUserRoles")
    @ResourceScan("查找当前用户拥有的所有角色")
    public Set<Role> findCurrentUserRoles(Long userId, String systemCode) {
        return roleService.findCurrentUserRoles(userId, systemCode);
    }


    /**
     * 修改用户的所属角色
     *
     * @param roles
     * @return
     */
    @RequestMapping("modifyUserRoles")
    @RequiresPermissions("base:user:modifyUserRoles")
    @ResourceScan("修改用户的所属角色")
    public R modifyUserRoles(@RequestParam(value = "roles") String[] roles, String userId) {
        try {
            userService.modifyUserRoles(roles, userId);
            return R.ok("修改用户角色组成功");
        } catch (Exception e) {
            return R.error("修改用户角色组失败，错误信息：" + e.getMessage());
        }
    }
}
