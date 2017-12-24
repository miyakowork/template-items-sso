package me.wuwenbin.items.sso.dao.model.pageVo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.items.sso.dao.entity.UserLoginLog;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * Created by wuwenbin on 2017/7/31.
 */
@Setter
@Getter
public class LoginLogVo extends UserLoginLog {

    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "user_id")
    private String username;

}
