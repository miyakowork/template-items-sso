package me.wuwenbin.items.sso.dao.model.pageVo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.OperationPrivilegeType;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * Created by wuwenbin on 2017/7/18.
 * <p>
 * 操作级权限类型VO
 */
@Setter
@Getter
public class OperationPrivilegeTypeVo extends OperationPrivilegeType {

    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "create_user")
    private String createUserName;
    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "update_user")
    private String updateUserName;

}
