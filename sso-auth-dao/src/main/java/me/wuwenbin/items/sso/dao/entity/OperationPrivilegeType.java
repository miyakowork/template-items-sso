package me.wuwenbin.items.sso.dao.entity;


import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

/**
 * Created by wuwenbin on 2017/7/12.
 * 操作级权限类型基础表
 */
@SQLTable("t_oauth_operation_privilege_type")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationPrivilegeType extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String name;

}
