package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.PrivilegeOperation;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/24 at 12:48
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface PrivilegeOperationRepository extends IPageAndSortRepository<PrivilegeOperation, Long> {

    /**
     * 根据编辑操作权限名称
     *
     * @param operationName
     * @param id
     * @return
     * @throws Exception
     */
    int updateOperationNameById(String operationName, long id) throws Exception;

    /**
     * 根据pagePrivilegeId查找页面级权限集合
     *
     * @param pagePrivilegeId
     * @return
     */
    List<PrivilegeOperation> findByPagePrivilegeId(Long pagePrivilegeId);
}
