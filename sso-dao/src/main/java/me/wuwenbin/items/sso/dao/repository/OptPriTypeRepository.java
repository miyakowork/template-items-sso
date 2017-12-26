package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.OperationPrivilegeType;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/24 at 12:46
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface OptPriTypeRepository extends IPageAndSortRepository<OperationPrivilegeType, Long> {

    /**
     * 编辑
     *
     * @param operationPrivilegeType
     * @return
     */
    @Modify(Router.DEFAULT)
    int updateById(OperationPrivilegeType operationPrivilegeType);

    /**
     * 查找所有可用的操作级权限类型
     *
     * @param enabled
     * @return
     */
    List<OperationPrivilegeType> findByEnabled(boolean enabled);
}
