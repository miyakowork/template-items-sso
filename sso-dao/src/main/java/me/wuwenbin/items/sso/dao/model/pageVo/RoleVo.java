package me.wuwenbin.items.sso.dao.model.pageVo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.Role;
import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;
import me.wuwenbin.modules.repository.annotation.sql.SQLRefer;

/**
 * 角色管理页面的对象VO
 * Created by wuwenbin on 2017/7/16.
 */
@Setter
@Getter
public class RoleVo extends Role {

    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "create_user")
    private String createName;
    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "update_user")
    private String updateName;
    @SQLPkRefer(targetClass = Role.class, targetColumn = "name", joinColumn = "parent_id")
    private String parentName;
    @SQLRefer(targetClass = SystemModule.class, targetColumn = "name", referColumn = "system_code", joinColumn = "system_code")
    private String systemModuleName;

}
