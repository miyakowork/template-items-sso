package me.wuwenbin.items.sso.dao.model.querybo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * 角色管理查询BO对象
 * Created by wuwenbin on 2017/7/8.
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_role")
public class RoleBo extends BootstrapTableQuery {

    @QueryColumn(column = "parent_id", operator = Operator.EQ)
    private String parentId;

    private String name;

    @QueryColumn(column = "enabled", operator = Operator.EQ)
    private Boolean selectEnabled;

    @QueryColumn(column = "cn_name")
    private String cnName;

    @QueryColumn(column = "system_code", operator = Operator.EQ)
    private String systemCode;

}
