package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.OperationPrivilegeType;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2017/12/24 at 12:46
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface OptPriTypeRepository extends IPageAndSortRepository<OperationPrivilegeType, Long> {
}
