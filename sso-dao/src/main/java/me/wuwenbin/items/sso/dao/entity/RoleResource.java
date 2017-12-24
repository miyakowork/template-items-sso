package me.wuwenbin.items.sso.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.items.sso.dao.constant.SQLRouters;
import me.wuwenbin.modules.sql.annotation.SQLColumn;

import java.io.Serializable;

import static me.wuwenbin.items.sso.dao.constant.SQLRouters.INSERT_ROLE_RESOURCE;

/**
 * created by Wuwenbin on 2017/12/24 at 13:18
 * @author wuwenbin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResource implements Serializable {

    @SQLColumn(routers = INSERT_ROLE_RESOURCE)
    private Long roleId;
    @SQLColumn(routers = INSERT_ROLE_RESOURCE)
    private Long resourceId;
    private boolean enabled;
}
