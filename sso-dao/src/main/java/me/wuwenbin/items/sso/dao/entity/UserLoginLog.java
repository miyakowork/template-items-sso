package me.wuwenbin.items.sso.dao.entity;

import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.GeneralType;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.time.LocalDateTime;

/**
 *
 * @author wuwenbin
 * @date 2017/7/12/012
 * 用户登陆日志
 */
@SQLTable("t_oauth_user_login_log")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginLog extends DataEntity<Long> {

    @SQLColumn(pk = true)
    @GeneralType
    private Long id;
    private Long userId;
    private LocalDateTime lastLoginDate;
    private String lastLoginIp;


}
