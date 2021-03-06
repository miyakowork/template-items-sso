package me.wuwenbin.items.sso.dao.model.querybo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 *
 * @author wuwenbin
 * @date 2017/7/14
 * 系统模块表BO
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_system_module")
public class SystemModuleBo extends BootstrapTableQuery {

    private String name;

    @QueryColumn(column = "system_code", operator = Operator.EQ)
    private String systemCode;

    @QueryColumn(operator = Operator.EQ)
    private Boolean enabled;


}
