package me.wuwenbin.items.sso.dao.entity;


import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import static me.wuwenbin.items.sso.dao.constant.SQLRouters.EDIT_USER;

/**
 * 用户表
 *
 * @author wuwenbin
 * @date 2017/7/12
 */
@SQLTable("t_oauth_user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String username;
    private String password;
    @SQLColumn(routers = EDIT_USER)
    private String cname;
    @SQLColumn(routers = EDIT_USER)
    private Long deptId;
    private String salt;
    @SQLColumn(routers = EDIT_USER)
    private String email;
    @SQLColumn(routers = EDIT_USER)
    private Long defaultRoleId;
    private String qq;
    private String wechat;
    @SQLColumn(routers = EDIT_USER)
    private String mobile;


    public String getCredentialsSalt() {
        return username + salt;
    }

}
