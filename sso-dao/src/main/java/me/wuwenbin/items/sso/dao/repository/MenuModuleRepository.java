package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.MenuModule;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.OrderBy;
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
    @OrderBy(value = "order_index")
    List<MenuModule> findByEnabledAndSystemCode(boolean enabled, String systemCode);
}
