package me.wuwenbin.items.sso.dao.entity;

import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

/**
 * 菜单模块基础表
 * Created by wuwenbin on 2017/7/12.
 */
@SQLTable("t_oauth_menu_module")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuModule extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String name;
    private String systemCode;
    private String iconLarger;
    private String iconMini;

}
