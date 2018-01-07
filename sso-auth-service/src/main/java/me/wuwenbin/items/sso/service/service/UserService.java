package me.wuwenbin.items.sso.service.service;

import me.wuwenbin.items.sso.dao.entity.User;


/**
 * created by Wuwenbin on 2017/12/25 at 18:21
 *
 * @author wuwenbin
 */
public interface UserService {

    /**
     * 修改用户角色组
     *
     * @param roleIds
     * @param userId
     * @throws Exception
     */
    void modifyUserRoles(String[] roleIds, String userId) throws Exception;


    /**
     * 修改密码
     *
     * @param user        用户对象
     * @param newPassword 新密码
     * @return
     * @throws Exception 修改出现的异常
     */
    int changePasswordByUser(User user, String newPassword) throws Exception;

    /**
     * 添加新用户
     *
     * @param user 新增的用户对象
     * @return 新增条数
     * @throws Exception 添加用户时出现的异常
     */
    int addNewUser(User user) throws Exception;
}
