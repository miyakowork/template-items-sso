package me.wuwenbin.items.sso.service.service;

import me.wuwenbin.items.sso.dao.entity.Menu;
import me.wuwenbin.items.sso.service.model.Ztree;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/23 at 17:59
 *
 * @author wuwenbin
 */
public interface MenuService {

    /**
     * 添加菜单
     *
     * @param menu
     * @return
     * @throws Exception
     */
    boolean addMenu(Menu menu) throws Exception;

    /**
     * 修改菜单内容
     *
     * @param menu
     * @return
     * @throws Exception
     */
    boolean editMenu(Menu menu) throws Exception;

    /**
     * 删除菜单
     *
     * @param id
     * @return
     * @throws Exception
     */
    void deleteMenu(Long id) throws Exception;

    /**
     * *添加菜单中获取当前添加菜单对应的权限资源，所对应的都是页面级的权限，
     * 应为菜单都是有页面的链接的，按钮级别的则应该不显示。按钮级别都应该对应各自的页面上
     *
     * @param resourceModuleId
     * @param roleId
     * @return
     */
    List<Ztree> findSelectMenuPrivilege(String resourceModuleId, String roleId);

    /**
     * 根据相关条件查找左侧菜单
     *
     * @param roleId
     * @param menuModuleId
     * @param systemCode
     * @return
     */
    List<Menu> findLeftMenuCached(long roleId, long menuModuleId, String systemCode);
}
