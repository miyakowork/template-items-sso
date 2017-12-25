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
     */
    @Routers(EDIT_USER)
    int editUserInfo(User user);

    @SQL("UPDATE t_oauth_user SET enabled = 0 WHERE id = :id")
    int updateUserStatus();
}
