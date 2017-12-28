package me.wuwenbin.items.sso.dao.model.querybo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * 部门管理页搜素面查询对象
 * Created by wuwenbin on 2017/08/04.
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_department")
public class DepartmentBo extends BootstrapTableQuery {

    /**
     * 部门名称
     */
    private String name;
    /**
     * 父节点id
     */
    @QueryColumn(column = "parent_id", operator = Operator.EQ)
    private Long parentId;
    /**
     * 是否可用
     */
    @QueryColumn(operator = Operator.EQ)
    private Boolean enabled;

}
