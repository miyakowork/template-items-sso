package me.wuwenbin.items.sso.dao.model.querybo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * 资源模块管理查询BO对象
 * Created by wuwenbin on 2017/8/8.
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_resource_module")
public class ResModuleBo extends BootstrapTableQuery {
    private String name;

    @QueryColumn(column = "enabled", operator = Operator.EQ)
    private String selectEnabled;

    @QueryColumn(column = "system_code", operator = Operator.EQ)
    private String systemCode;

}
