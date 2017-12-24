package me.wuwenbin.items.sso.dao.entity;

import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

/**
 * Created by wuwenbin on 2017/7/12/012.
 * 资源表
 */
@SQLTable("t_oauth_resource")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resource extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String url;
    private String permissionMark;
    private String name;
    private String systemCode;


}
