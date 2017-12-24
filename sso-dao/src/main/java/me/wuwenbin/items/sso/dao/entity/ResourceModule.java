package me.wuwenbin.items.sso.dao.entity;


import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

/**
 * Created by wuwenbin on 2017/7/12/012.
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

}
