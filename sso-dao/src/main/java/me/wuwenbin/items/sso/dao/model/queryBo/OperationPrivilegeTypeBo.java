package me.wuwenbin.items.sso.dao.model.queryBo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * Created by wuwenbin on 2017/7/12.
 * <p>
 * 操作级权限类型查询BO对象
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_operation_privilege_type")
public class OperationPrivilegeTypeBo extends BootstrapTableQuery {

    private String name;

    @QueryColumn(operator = Operator.EQ)
    public Boolean enabled;

}
