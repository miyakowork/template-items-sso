package me.wuwenbin.items.sso.dao.entity;


import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import static me.wuwenbin.items.sso.dao.constant.SQLRouters.EDIT_MENU;

/**
 * 菜单表
 *
 * @author wuwenbin
 * @date 2017/7/12
 */

@SQLTable("t_oauth_menu")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    @SQLColumn(routers = EDIT_MENU)
    private String name;
    private Long resourceId;
    private String systemCode;
    @SQLColumn(routers = EDIT_MENU)
    private String icon;
    @SQLColumn(routers = EDIT_MENU)
    private String iconLarger;
    private String menuType;
    private String href;
    private String onclick;
    private String target;
    private Long roleId;
    private Long menuModuleId;
    @SQLColumn(routers = EDIT_MENU)
    private Long parentId;

    /**
     * 生成root节点
     *
     * @return root
     */
    public static Menu root() {
        Menu m = Menu.builder().id(0L).name("菜单根节点").parentId(-1L).build();
        m.setRemark("根节点菜单");
        return m;
    }

    @Override
    public String nodeId() {
        return getId().toString();
    }

    @Override
    public String nodePId() {
        return getParentId().toString();
    }

    @Override
    public String nodeName() {
        return getName();
    }

    @Override
    public boolean nodeOpen() {
        return true;
    }
}
