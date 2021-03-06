package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.PrivilegePage;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/24 at 12:50
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface PrivilegePageRepository extends IPageAndSortRepository<PrivilegePage, Long>, IBaseCrudRepository<PrivilegePage, Long> {

    /**
     * 根据资源模块id查找页面级权限
     *
     * @param resourceModuleId
     * @return
     */
    List<PrivilegePage> findByResourceModuleId(String resourceModuleId);

    /**
     * 修改
     *
     * @param privilegePage
     * @return
     */
    @Modify(Router.DEFAULT)
    int updateById(PrivilegePage privilegePage);

}
