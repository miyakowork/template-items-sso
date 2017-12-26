package me.wuwenbin.items.sso.service.config.matcher;

import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.entity.UserLoginLog;
import me.wuwenbin.items.sso.dao.repository.UserLoginLogRepository;
import me.wuwenbin.items.sso.dao.repository.UserRepository;
import me.wuwenbin.items.sso.service.config.password.PasswordHelper;
import me.wuwenbin.items.sso.service.constant.CacheConsts;
import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import me.wuwenbin.items.sso.service.support.util.HttpUtils;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 凭证匹配器自定义实现
 *
 * @author wuwenbin
 * @date 2017/2/6
 */
@Component
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyCredentialsMatcher.class);

    @Autowired
    private PasswordHelper passwordHelper;
    @Autowired
    private CacheManager cacheManager;

    private UserRepository userRepository = RepositoryFactory.get(UserRepository.class);
    private UserLoginLogRepository loginLogRepository = RepositoryFactory.get(UserLoginLogRepository.class);
    private Cache<String, AtomicInteger> passwordRetryCache;

    @Autowired
    public void setPasswordRetryCache() {
        this.passwordRetryCache = cacheManager.getCache(CacheConsts.PASSWORD_RETRY_CACHE);
    }


    @Override
    public boolean doCredentialsMatch(AuthenticationToken authToken, AuthenticationInfo info) {
        String username = (String) authToken.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if (retryCount.incrementAndGet() > 5) {
            //if retry count > 5 throw
            throw new ExcessiveAttemptsException();
        }

        Object tokenCredentials = encrypt(authToken);
        Object accountCredentials = getCredentials(info);

        boolean matches = equals(tokenCredentials, accountCredentials);
        if (matches) {
            //clear retry count
            passwordRetryCache.remove(username);

            HttpServletRequest request = HttpUtils.getRequest();
            HttpSession session = request.getSession();
            //验证用户成功了，把用户名信息放到session中去
            session.setAttribute(ShiroConsts.SESSION_USERNAME_KEY, username);
            //验证用户成功了，删除强制登录的标识
            session.removeAttribute(ShiroConsts.SESSION_FORCE_LOGOUT_KEY);
            try {
                Long userId = userRepository.findByUsername(username).getId();
                UserLoginLog userLoginLog = UserLoginLog.builder().userId(userId).lastLoginDate(LocalDateTime.now()).lastLoginIp(HttpUtils.getRemoteAddr(request)).build();
                userLoginLog.setEnabled(true);
                userLoginLog.setUpdateDate(LocalDateTime.now());
                loginLogRepository.save(userLoginLog);
            } catch (Exception e) {
                LOGGER.error("记录用户登录日志失败，异常信息：{}", e);
            }
        }
        return matches;
    }

    /**
     * 将前台传递进来的密码使用自定义的方式进行加密处理进行匹配
     *
     * @return
     */
    private String encrypt(AuthenticationToken authToken) {
        UsernamePasswordToken token = (UsernamePasswordToken) authToken;
        String username = token.getUsername();
        char[] password = token.getPassword();
        String plainPassword = new String(Base64.decode(String.valueOf(password)));
        User user = userRepository.findByUsername(username);
        if (user == null) {
            /*
             * 没找到帐号
             */
            throw new UnknownAccountException();
        } else {
            return passwordHelper.getPassword(user, plainPassword);
        }
    }


}
