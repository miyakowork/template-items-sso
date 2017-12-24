package me.wuwenbin.items.sso.dao.model.queryBo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * Created by wuwenbin on 2017/7/19/019.
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_user")
public class UserBo extends BootstrapTableQuery {

    private String username;

    private String cname;

    @QueryColumn(operator = Operator.EQ)
    private String enabled;

    @QueryColumn(operator = Operator.GTE, column = "create_date")
    private String createDate;

}
