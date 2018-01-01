package me.wuwenbin.items.sso.service.support.util;

import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * 用户工具类
 *
 * @author Wuwenbin
 * @date 2017/7/17
 */
public class UserUtils {
    /**
     * 用户服务对象
     */
    private static UserRepository userRepository = RepositoryFactory.get(UserRepository.class);

    /**
     * 获取当前访问用户名
     *
     * @return 当前登录用户名
     */
    public static String getLoginUserName() {
        Subject subject = SecurityUtils.getSubject();
        return (String) subject.getPrincipal();
    }

    /**
     * 获取当前登录用户
     *
     * @return 当前登录用户
     */
    public static User getLoginUser() {
        Subject subject = SecurityUtils.getSubject();
        String userName = (String) subject.getPrincipal();
        return userRepository.findByUsername(userName);
    }

}
