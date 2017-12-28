package me.wuwenbin.items.sso.dao.model.querybo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * created by wuwenbin on 2017/8/22 at 15:58
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_privilege_operation")
public class PrivilegeOperationBo extends LayTableQuery {

    @QueryColumn(column = "operation_name")
    private String privilegeOperationName;

    @QueryColumn(column = "page_privilege_id", operator = Operator.EQ)
    private String pagePrivilegeId;

}
