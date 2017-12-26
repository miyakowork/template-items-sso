package me.wuwenbin.items.sso.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.io.Serializable;

import static me.wuwenbin.items.sso.dao.constant.SQLRouters.INSERT_ROLE_RESOURCE;

/**
 * created by Wuwenbin on 2017/12/24 at 13:18
 *
 * @author wuwenbin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_oauth_role_resource")
public class RoleResource implements Serializable {

    @SQLColumn(routers = INSERT_ROLE_RESOURCE)
    private Long roleId;
    @SQLColumn(routers = INSERT_ROLE_RESOURCE)
    private Long resourceId;
    private boolean enabled;
}
