package me.wuwenbin.items.sso.dao.model.querybo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * Created by wuwenbin on 2017/8/12.
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_menu_module")
public class MenuModuleBo extends BootstrapTableQuery {

    private String name;

    @QueryColumn(column = "system_code", operator = Operator.EQ)
    private String systemCodeName;

    @QueryColumn(operator = Operator.EQ)
    public Boolean enabled;

}
