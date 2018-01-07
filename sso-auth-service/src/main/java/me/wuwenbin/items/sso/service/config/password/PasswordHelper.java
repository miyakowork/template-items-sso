package me.wuwenbin.items.sso.service.config.password;

import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.utils.security.Encrypt;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

/**
 * 用户密码操作
 * 可实现加密用户密码等
 *
 * @author wuwenbin
 * @date 2017/4/18
 */
@Component
public class PasswordHelper {

    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private String algorithmName = "md5";
    private int hashIterations = 2;

    public void setRandomNumberGenerator(RandomNumberGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void setAlgorithmName() {
        this.algorithmName = "md5";
    }

    public void setHashIterations() {
        this.hashIterations = 2;
    }

    /**
     * 密码加密
     *
     * @param user 用户对象
     */
    public void encryptPasswordByPlain(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        user.setPassword(Encrypt.digest.md5Hex(user.getPassword()));
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }

    /**
     * 密码加密，此处的用户对象中的密码已被md5加密过一次
     *
     * @param user 用户对象
     */
    public void encryptPasswordByMd5(User user) {
        user.setSalt(randomNumberGenerator.nextBytes().toHex());
        String newPassword = new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
        user.setPassword(newPassword);
    }

    /**
     * 加密User，同时返回加密之后的密码
     *
     * @param user 用户对象
     * @return 加密后的用户密码
     */
    public String getPasswordByPlain(User user) {
        user.setPassword(Encrypt.digest.md5Hex(user.getPassword()));
        return new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
    }

    /**
     * 加密User（此处的密码已被MD5加密过一次），同时返回加密之后的密码
     *
     * @param user 用户对象
     * @return 加密后的用户密码
     */
    public String getPasswordByMd5(User user) {
        return new SimpleHash(algorithmName, user.getPassword(),
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
    }

    /**
     * 根据明文密码获取加密密码
     *
     * @param user          不含密码的用户对象
     * @param plainPassword 明文密码
     * @return 加密后的密码
     */
    public String getPasswordByPlain(User user, String plainPassword) {
        plainPassword = Encrypt.digest.md5Hex(plainPassword);
        return new SimpleHash(algorithmName, plainPassword,
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();
    }

    /**
     * 根据md5加密后过的密码在此进行加密
     *
     * @param user
     * @param md5Password
     * @return
     */
    public String getPasswordByMd5(User user, String md5Password) {
        return new SimpleHash(algorithmName, md5Password,
                ByteSource.Util.bytes(user.getCredentialsSalt()), hashIterations).toHex();
    }

}
