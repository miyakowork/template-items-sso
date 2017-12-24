package me.wuwenbin.items.sso.dao.model.pageVo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.MenuModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * Created by wuwenbin on 2017/7/17/017.
 */
@Setter
@Getter
public class MenuModuleVo extends MenuModule {

    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "create_user")
    private String createUsername;
    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "update_user")
    private String updateUsername;

}
