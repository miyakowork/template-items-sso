package me.wuwenbin.items.sso.dao.model.querybo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * Created by Wuwenbin on 2017/7/20.
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_session")
public class SessionBo extends BootstrapTableQuery {

    private String username;
    private String ip;
    @QueryColumn(column = "last_visit_date", operator = Operator.BETWEEN_AND)
    private String lastVisitDate;
    @QueryColumn(column = "first_visit_date", operator = Operator.GTE)
    private String firstVisitDate;

}
