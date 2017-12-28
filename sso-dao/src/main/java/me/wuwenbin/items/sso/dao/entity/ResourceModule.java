package me.wuwenbin.items.sso.dao.entity;


import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

/**
 * @author wuwenbin
 * @date 2017/7/12/012
 * 资源模块
 */
@SQLTable("t_oauth_resource_module")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceModule extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String name;
    private String systemCode;

    @Override
    public String nodeId() {
        return getId().toString();
    }

    @Override
    public String nodePId() {
        return "0";
    }

    @Override
    public String nodeName() {
        return getName();
    }

    @Override
    public Object nodeOther() {
        return getSystemCode();
    }
}
