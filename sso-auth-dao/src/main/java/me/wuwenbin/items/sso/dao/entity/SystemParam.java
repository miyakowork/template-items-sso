package me.wuwenbin.items.sso.dao.entity;

import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

/**
 * Created by wuwenbin on 2017/7/12/012.
 * 系统参数
 */
@SQLTable("t_oauth_system_param")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemParam extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String name;
    private String value;

}
