package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.Menu;
import me.wuwenbin.modules.repository.annotation.field.Routers;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.ListMap;
import me.wuwenbin.modules.repository.provider.find.annotation.OrderBy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static me.wuwenbin.items.sso.dao.constant.SQLRouters.EDIT_MENU;
import static me.wuwenbin.items.sso.dao.constant.SQLRouters.FIND_LEFT_MENU;

/**
 * created by Wuwenbin on 2017/12/23 at 17:33
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface MenuRepository extends IPageAndSortRepository<Menu, Long>, IBaseCrudRepository<Menu, Long> {

    /**
     * 根据菜单模块查找可用的菜单
     *
     * @param menuModuleId
     * @param roleId
     * @param enabled
     * @return
     */
    List<Menu> findByMenuModuleIdAndRoleIdAndEnabled(String menuModuleId, String roleId, boolean enabled);

    /**
     * 查找所有可用菜单
     *
     * @param enabled
     * @return
     */
    List<Menu> findByEnabled(boolean enabled);

    /**
     * 查询某个角色对应的可用菜单
     *
     * @param roleId
     * @param enabled
     * @return
     */
    List<Menu> findByRoleIdAndEnabled(String roleId, boolean enabled);

    /**
     * 修改菜单基本信息
     *
     * @param menu
     * @return
     * @throws Exception
     */
    @Routers(EDIT_MENU)
    int updateMenuInfo(Menu menu) throws Exception;

    /**
     * 删除id或者parentId为i当前值的菜单记录
     *
     * @param id
     * @param parentId
     * @return
     * @throws Exception
     */
    void deleteByIdOrParentId(Long id, Long parentId) throws Exception;

    /**
     * 根据资源模块id和角色id获取privilegePage对象和url的Map组合的list集合对象
     *
     * @param resourceModuleId
     * @param roleId
     * @return
     */
    @SQL("SELECT topp.*,tor.url FROM t_oauth_privilege_page topp LEFT JOIN t_oauth_resource tor ON tor.id = topp.resource_id " +
            "WHERE topp.resource_module_id = ? AND topp.resource_id IN " +
            "(SELECT torr.resource_id FROM t_oauth_role_resource torr WHERE torr.role_id = ?)")
    @ListMap
    List<Map<String, Object>> findPrivilegePageList(String resourceModuleId, String roleId);

    /**
     * 根据角色id、菜单模块id、系统模块查找对应的可用菜单
     *
     * @param roleId
     * @param menuModuleId
     * @param systemCode
     * @param enabled
     * @return
     */
    @Routers(FIND_LEFT_MENU)
    @OrderBy("order_index")
    List<Menu> findLeftMenuByRouters(String systemCode, long roleId, long menuModuleId, Boolean enabled);


}
