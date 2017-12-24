package me.wuwenbin.items.sso.dao.model.queryBo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.layui.LayTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * created by Wuwenbin on 2017/8/21 at 16:36
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_resource")
public class ResourceLayBo extends LayTableQuery {
    //搜索的资源名称
    private String name;
    //资源所属系统
    @QueryColumn(column = "system_code", operator = Operator.EQ)
    private String systemModuleCode;

}
