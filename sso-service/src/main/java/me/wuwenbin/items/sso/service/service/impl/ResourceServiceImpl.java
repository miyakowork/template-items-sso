package me.wuwenbin.items.sso.service.service.impl;

import me.wuwenbin.items.sso.dao.model.pageVo.ResourceVo;
import me.wuwenbin.items.sso.dao.repository.ResourceRepository;
import me.wuwenbin.items.sso.service.service.ResourceService;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.query.TableQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * created by Wuwenbin on 2017/12/25 at 17:59
 *
 * @author wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceRepository resourceRepository;

    @Override
    public Page<ResourceVo> findResourceSelectPage(TableQuery resourceBo, Page<ResourceVo> page) {
        String sql = "SELECT tor.*, tou1.username AS create_name, tou2.username AS update_name, tosm.name AS systemModuleName" +
                " FROM t_oauth_resource tor " +
                "LEFT JOIN t_oauth_user tou1 ON tor.create_user = tou1.id " +
                "LEFT JOIN t_oauth_user tou2 ON tor.update_user = tou2.id  " +
                "LEFT JOIN t_oauth_system_module tosm ON tosm.system_code = tor.system_code " +
                "WHERE tor.id NOT " +
                "IN (SELECT resource_id FROM t_oauth_privilege_operation UNION SELECT resource_id FROM t_oauth_privilege_page) ";
        return resourceRepository.findPagination(sql, page, ResourceVo.class, resourceBo);
    }

}
