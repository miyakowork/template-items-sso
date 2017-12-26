package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.MenuModule;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.OrderBy;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/23 at 17:29
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface MenuModuleRepository extends IPageAndSortRepository<MenuModule, Long> {

    /**
     * 根据系统模块代码查找可用的菜单模块集合
     *
     * @param enabled
     * @param systemCode
     * @return
     */
    @OrderBy("order_index")
    List<MenuModule> findByEnabledAndSystemCode(boolean enabled, String systemCode);

    /**
     * 根据角色id和系统模块代码查找该角色对应的所有可用的菜单模块
     *
     * @param roleId
     * @param systemCode
     * @return
     */
    @SQL("SELECT tomm.* FROM t_oauth_menu_module tomm WHERE tomm.id " +
            "IN (SELECT DISTINCT tom.menu_module_id FROM t_oauth_menu tom WHERE tom.role_id = ? AND tom.system_code = ?) " +
            "AND tomm.enabled = 1")
    List<MenuModule> findMenuModuleByRoleIdAndSystemCode(Long roleId, String systemCode);

    /**
     * 修改
     *
     * @param menuModule
     * @return
     */
    @Modify(Router.DEFAULT)
    int updateById(MenuModule menuModule);

    /**
     * 查找所有可用的菜单模块
     *
     * @param enabled
     * @return
     */
    List<MenuModule> findByEnabled(boolean enabled);
}
