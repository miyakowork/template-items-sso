package me.wuwenbin.items.sso.dao.model.pageVo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.ResourceModule;
import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;
import me.wuwenbin.modules.repository.annotation.sql.SQLRefer;

/**
 * 资源模块页面的对象VO
 * Created by wuwenbin on 2017/7/16.
 */
@Setter
@Getter
public class ResourceModuleVo extends ResourceModule {

    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "create_user")
    private String createUserName;
    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "update_user")
    private String updateUserName;
    @SQLRefer(targetClass = SystemModule.class, targetColumn = "name", referColumn = "system_code", joinColumn = "system_code")
    private String systemModuleName;


}
