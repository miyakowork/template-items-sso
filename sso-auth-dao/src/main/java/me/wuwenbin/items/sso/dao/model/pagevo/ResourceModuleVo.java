package me.wuwenbin.items.sso.dao.model.pagevo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.ResourceModule;
import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;
import me.wuwenbin.modules.repository.annotation.sql.SQLRefer;

/**
 * 资源模块页面的对象VO
 *
 * @author wuwenbin
 * @date 2017/7/16
 */
@Setter
@Getter
public class ResourceModuleVo extends ResourceModule {

    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tau1", targetColumn = "username", joinColumn = "create_user")
    private String createUserName;
    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tau2", targetColumn = "username", joinColumn = "update_user")
    private String updateUserName;
    @SQLRefer(targetClass = SystemModule.class, targetColumn = "name", referColumn = "system_code", joinColumn = "system_code")
    private String systemModuleName;


}
