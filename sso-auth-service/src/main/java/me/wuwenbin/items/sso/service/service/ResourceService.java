package me.wuwenbin.items.sso.service.service;

import me.wuwenbin.items.sso.dao.model.pagevo.ResourceVo;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.query.TableQuery;

/**
 * created by Wuwenbin on 2017/12/25 at 17:58
 *
 * @author wuwenbin
 */
public interface ResourceService {

    /**
     * 根据权限标识查询数据
     *
     * @param resourceBo
     * @param page
     * @return
     */
    Page<ResourceVo> findResourceSelectPage(TableQuery resourceBo, Page<ResourceVo> page);
}
