package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.ResourceModule;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/24 at 13:34
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface ResourceModuleRepository extends IPageAndSortRepository<ResourceModule, Long> {

    /**
     * 根据可用状态和系统代码查询可用的资源模块
     *
     * @param systemModuleCode
     * @param enabled
     * @return
     */
    List<ResourceModule> findBySystemModuleCodeAndEnabled(String systemModuleCode, boolean enabled);

}
