package me.wuwenbin.items.sso.dao.entity;


import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

/**
 * Created by wuwenbin on 2017/7/12.
 * 操作级权限表
 */
@SQLTable("t_oauth_privilege_operation")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegeOperation extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String operationName;
    private Long resourceId;
    private Long pagePrivilegeId;
    private Long operationTypeId;

}
