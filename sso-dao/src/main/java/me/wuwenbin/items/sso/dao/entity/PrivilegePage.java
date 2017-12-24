package me.wuwenbin.items.sso.dao.entity;


import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

/**
 * Created by wuwenbin on 2017/7/12.
 * 页面级权限表
 */
@SQLTable("t_oauth_privilege_page")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrivilegePage extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String name;
    private Long resourceId;
    private Long resourceModuleId;
}
