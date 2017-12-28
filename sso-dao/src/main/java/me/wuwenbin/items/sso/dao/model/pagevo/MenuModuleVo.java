package me.wuwenbin.items.sso.dao.model.pagevo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.MenuModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 *
 * @author wuwenbin
 * @date 2017/7/17/017
 */
@Setter
@Getter
public class MenuModuleVo extends MenuModule {

    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tau1", targetColumn = "username", joinColumn = "create_user")
    private String createUsername;
    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tau2", targetColumn = "username", joinColumn = "update_user")
    private String updateUsername;

}
