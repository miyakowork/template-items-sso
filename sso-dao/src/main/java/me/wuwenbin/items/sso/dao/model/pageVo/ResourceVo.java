package me.wuwenbin.items.sso.dao.model.pageVo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.Resource;
import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;
import me.wuwenbin.modules.repository.annotation.sql.SQLRefer;

/**
 * Created by wuwenbin on 2017/7/17.
 */

@Setter
@Getter
public class ResourceVo extends Resource {

    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "create_user")
    private String createName;
    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "update_user")
    private String updateName;
    @SQLRefer(targetClass = SystemModule.class, targetColumn = "name", referColumn = "system_code", joinColumn = "system_code")
    private String systemModuleName;


}
