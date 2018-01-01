package me.wuwenbin.items.sso.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.io.Serializable;

/**
 * created by Wuwenbin on 2017/12/25 at 12:45
 *
 * @author wuwenbin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLTable("t_oauth_user_role")
public class UserRole implements Serializable {

    private Long userId;
    private Long roleId;
    private Boolean enabled;
}
