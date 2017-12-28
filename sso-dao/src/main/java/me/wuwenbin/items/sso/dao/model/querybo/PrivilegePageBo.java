package me.wuwenbin.items.sso.dao.model.querybo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * Created by wuwenbin on 2017/7/19.
 * 页面资源表BO
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_privilege_page")
public class PrivilegePageBo extends BootstrapTableQuery {

    //资源模块名称
    private String name;
    @QueryColumn(operator = Operator.EQ)
    public Boolean enabled;
    //资源模块ID
    @QueryColumn(column = "resource_module_id", operator = Operator.EQ)
    private String moduleId;
    //资源名称
    @QueryColumn(column = "name", tableName = "t_oauth_resource")
    private String resourceName;

}
