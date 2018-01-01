package me.wuwenbin.items.sso.dao.model.pagevo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 *
 * @author wuwenbin
 * @date 2017/7/17
 * 系统模块 VO
 */
@Setter
@Getter
public class SystemModuleVo extends SystemModule {

    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tau1", targetColumn = "username", joinColumn = "create_user")
    private String createUserName;
    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tau2", targetColumn = "username", joinColumn = "update_user")
    private String updateUserName;

}
