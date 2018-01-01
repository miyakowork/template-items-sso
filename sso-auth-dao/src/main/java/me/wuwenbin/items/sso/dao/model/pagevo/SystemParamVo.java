package me.wuwenbin.items.sso.dao.model.pagevo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.SystemParam;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * 系统参数页面的对象VO
 *
 * @author wuwenbin
 * @date 2017/7/16
 */
@Setter
@Getter
public class SystemParamVo extends SystemParam {

    @SQLPkRefer(targetClass = User.class, targetTableAlias = "ua1", targetColumn = "username", joinColumn = "create_user")
    private String createUserName;
    @SQLPkRefer(targetClass = User.class, targetTableAlias = "ua2", targetColumn = "username", joinColumn = "update_user")
    private String updateUserName;

}
