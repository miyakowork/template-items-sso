package me.wuwenbin.items.sso.server.service.impl;

import me.wuwenbin.items.sso.dao.entity.Menu;
import me.wuwenbin.items.sso.dao.repository.MenuRepository;
import me.wuwenbin.items.sso.server.model.Ztree;
import me.wuwenbin.items.sso.server.service.MenuService;
import me.wuwenbin.items.sso.server.support.collector.PagePrivilegeTreeCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2017/12/23 at 18:02
 *
 * @author wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public boolean editMenu(Menu menu) throws Exception {
        return false;
    }

    @Override
    public boolean deleteMenu(String id) throws Exception {
        return false;
    }

    @Override
    public List<Ztree> findSelectMenuPrivilege(String resourceModuleId, String roleId) {
        List<Map<String, Object>> privilegePageList = menuRepository.findPrivilegePageList(resourceModuleId, roleId);
        return privilegePageList.stream().collect(new PagePrivilegeTreeCollector());
    }
}
