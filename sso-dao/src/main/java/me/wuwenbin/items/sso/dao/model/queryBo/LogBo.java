package me.wuwenbin.items.sso.dao.model.queryBo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * Created by wuwenbin on 2017/7/13/013.
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_user_login_log")
public class LogBo extends BootstrapTableQuery {

    private String username;

    @QueryColumn(column = "last_login_date", operator = Operator.GTE)
    private String lastLoginDate;

}
