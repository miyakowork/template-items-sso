package me.wuwenbin.items.sso.dao.model.queryBo;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.pagination.query.model.bootstrap.BootstrapTableQuery;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryColumn;
import me.wuwenbin.modules.pagination.query.support.annotation.QueryTable;
import me.wuwenbin.modules.pagination.query.support.operator.Operator;

/**
 * Created by wuwenbin on 2017/7/13.
 */
@Setter
@Getter
@QueryTable(name = "t_oauth_resource")
public class ResourceBo extends BootstrapTableQuery {
    //资源路径
    private String url;
    //资源标识
    @QueryColumn(column = "permission_mark")
    private String permissionMark;
    //系统代码
    @QueryColumn(column = "system_code", operator = Operator.EQ)
    private String systemCode;
    //名称
    private String name;
    //是否可用
    @QueryColumn(operator = Operator.EQ)
    public Boolean enabled;


}
