package me.wuwenbin.items.sso.dao.model.pagevo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.Resource;
import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;
import me.wuwenbin.modules.repository.annotation.sql.SQLRefer;

/**
 *
 * @author wuwenbin
 * @date 2017/7/17
 */

@Setter
@Getter
public class ResourceVo extends Resource {

    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tau1", targetColumn = "username", joinColumn = "create_user")
    private String createName;
    @SQLPkRefer(targetClass = User.class, targetTableAlias = "tau2", targetColumn = "username", joinColumn = "update_user")
    private String updateName;
    @SQLRefer(targetClass = SystemModule.class, targetColumn = "name", referColumn = "system_code", joinColumn = "system_code")
    private String systemModuleName;


}
