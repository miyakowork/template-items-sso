package me.wuwenbin.items.sso.service.service.impl;

import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.entity.UserRole;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.items.sso.dao.repository.UserRoleRepository;
import me.wuwenbin.items.sso.service.config.password.PasswordHelper;
import me.wuwenbin.items.sso.service.service.UserService;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * created by Wuwenbin on 2017/12/25 at 18:22
 *
 * @author wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private UserRepository userRepository;
    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public void modifyUserRoles(String[] roleIds, String userId) throws Exception {
        userRoleRepository.deleteByUserId(userId);
        Collection<UserRole> userRoles = new ArrayList<>(roleIds.length);
        Arrays.stream(roleIds).forEach(rId -> {
            UserRole ur = UserRole.builder().userId(Long.valueOf(userId)).roleId(Long.valueOf(rId)).enabled(true).build();
            userRoles.add(ur);
        });
        userRoleRepository.save(userRoles);
    }

    @Override
    public int changePasswordByUser(User user, String newPassword) throws Exception {
        newPassword = passwordHelper.getPasswordByPlain(user, newPassword);
        user.setPassword(newPassword);
        user.preUpdate(UserUtils.getLoginUser()::getId);
        return userRepository.updatePasswordById(user.getPassword(), user.getId());
    }

    @Override
    public int addNewUser(User user) throws Exception {
        user.preInsert(UserUtils.getLoginUser()::getId);
        passwordHelper.encryptPasswordByPlain(user);
        User newUser = userRepository.save(user);
        UserRole ur = UserRole.builder().userId(newUser.getId()).roleId(newUser.getDefaultRoleId()).enabled(true).build();
        return userRoleRepository.saveUserRole(ur);
    }
}
