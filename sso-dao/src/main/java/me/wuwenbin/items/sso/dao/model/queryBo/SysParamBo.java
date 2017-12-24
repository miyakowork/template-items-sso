package me.wuwenbin.items.sso.dao.model.queryBo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * 系统参数查询BO对象
 * Created by wuwenbin on 2017/7/8.
 */
@QueryTable(name = "t_oauth_system_param")
@Getter
@Setter
public class SysParamBo extends BootstrapTableQuery {

    private String name;

    @QueryColumn(column = "enabled", operator = Operator.EQ)
    private Boolean selectEnabled;

    private String value;

}
