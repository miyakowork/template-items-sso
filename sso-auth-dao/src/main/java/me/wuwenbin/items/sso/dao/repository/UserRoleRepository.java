package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.UserRole;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.provider.save.annotation.SaveSQL;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2017/12/25 at 18:23
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UserRoleRepository extends IBaseCrudRepository<UserRole, Long> {

    /**
     * 根据userId删除记录
     *
     * @param userId
     * @throws Exception
     */
    void deleteByUserId(String userId) throws Exception;

    /**
     * 插入一条UserRole记录
     *
     * @param userRole
     * @return
     */
    @SaveSQL(columns = {"user_id", "role_id", "enabled"})
    int saveUserRole(UserRole userRole);
}
