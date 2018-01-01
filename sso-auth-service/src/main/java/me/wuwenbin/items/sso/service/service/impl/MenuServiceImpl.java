package me.wuwenbin.items.sso.service.service.impl;

import me.wuwenbin.items.sso.dao.entity.Menu;
import me.wuwenbin.items.sso.dao.repository.MenuRepository;
import me.wuwenbin.items.sso.service.constant.CacheConsts;
import me.wuwenbin.items.sso.service.model.Ztree;
import me.wuwenbin.items.sso.service.service.MenuModuleService;
import me.wuwenbin.items.sso.service.service.common.PublicServices;
import me.wuwenbin.items.sso.service.support.collector.PagePrivilegeTreeCollector;
import me.wuwenbin.items.sso.service.support.util.UserUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2017/12/23 at 18:02
 *
 * @author wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuModuleService {

    @Resource
    private MenuRepository menuRepository;

    @Override
    public boolean addMenu(Menu menu) throws Exception {
        PublicServices.clearCache(CacheConsts.MENU_CACHE, () -> {
            String roleId = UserUtils.getLoginUser().getDefaultRoleId().toString();
            String menuModuleId = menu.getMenuModuleId().toString();
            return String.join(":", menu.getSystemCode(), roleId, menuModuleId);
        });
        menu.preInsert(UserUtils.getLoginUser()::getId);
        return menuRepository.save(menu) != null;
    }

    @Override
    public synchronized boolean editMenu(Menu menu) throws Exception {
        PublicServices.clearCache(CacheConsts.MENU_CACHE, () -> {
            String roleId = UserUtils.getLoginUser().getDefaultRoleId().toString();
            Menu oldMenu = menuRepository.findOne(menu.getId());
            String menuModuleId = oldMenu.getMenuModuleId().toString();
            return String.join(":", oldMenu.getSystemCode(), roleId, menuModuleId);
        });
        menu.preUpdate(UserUtils.getLoginUser()::getId);
        return menuRepository.editMenuInfo(menu) == 1;
    }

    @Override
    public synchronized void deleteMenu(Long id) throws Exception {
        PublicServices.clearCache(CacheConsts.MENU_CACHE, () -> {
            String roleId = UserUtils.getLoginUser().getDefaultRoleId().toString();
            Menu oldMenu = menuRepository.findOne(id);
            String menuModuleId = oldMenu.getMenuModuleId().toString();
            return String.join(":", oldMenu.getSystemCode(), roleId, menuModuleId);
        });
        menuRepository.deleteByIdOrParentId(id, id);
    }

    @Override
    public List<Ztree> findSelectMenuPrivilege(String resourceModuleId, String roleId) {
        List<Map<String, Object>> privilegePageList = menuRepository.findPrivilegePageList(resourceModuleId, roleId);
        return privilegePageList.stream().collect(new PagePrivilegeTreeCollector());
    }

    @Override
    @Cacheable(value = CacheConsts.MENU_CACHE, key = "#systemCode.concat(':').concat(#roleId).concat(':').concat(#menuModuleId)")
    public List<Menu> findLeftMenuCached(long roleId, long menuModuleId, String systemCode) {
        return menuRepository.findLeftMenuByRouters(systemCode, roleId, menuModuleId, Boolean.TRUE);
    }


}
