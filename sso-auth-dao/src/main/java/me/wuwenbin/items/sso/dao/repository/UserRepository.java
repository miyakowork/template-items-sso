package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.field.Routers;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import org.springframework.transaction.annotation.Transactional;

import static me.wuwenbin.items.sso.dao.constant.SQLRouters.EDIT_USER;

/**
 * created by Wuwenbin on 2017/12/23 at 18:18
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UserRepository extends IPageAndSortRepository<User, Long> {

    /**
     * 修改用户基本信息
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Routers(EDIT_USER)
    int updateUserInfo(User user) throws Exception;

    /**
     * 批量更改用户为不可用状态
     *
     * @param ids
     * @return
     * @throws Exception
     */
    @SQL("UPDATE t_oauth_user SET enabled = 0 WHERE id = :id")
    int updateUserStatus(String[] ids) throws Exception;

    /**
     * 根据用户id修改该用户的默认角色id
     *
     * @param defaultRoleId
     * @param userId
     * @return
     * @throws Exception
     */
    int updateDefaultRoleIdById(long defaultRoleId, long userId) throws Exception;

    /**
     * 通过用户名查找user对象
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 更新用户密码
     *
     * @param password
     * @param id
     * @return
     * @throws Exception
     */
    int updatePasswordById(String password, long id) throws Exception;

}
