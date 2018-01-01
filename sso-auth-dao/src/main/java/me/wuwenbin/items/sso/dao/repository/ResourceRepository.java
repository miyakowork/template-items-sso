package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.Resource;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.PrimitiveCollection;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/24 at 13:36
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface ResourceRepository extends IPageAndSortRepository<Resource, Long> {

    /**
     * 根据roleId查找该roleId所含有的权限标识
     *
     * @param roleId
     * @return
     */
    @SQL("SELECT tor.permission_mark AS pm FROM t_oauth_resource tor" +
            " WHERE tor.enabled = 1 AND tor.id  " +
            " IN (SELECT torr.RESOURCE_ID FROM t_oauth_role_resource torr" +
            " WHERE torr.enabled = 1 AND torr.resource_id = tor.ID AND torr.role_id = ?)")
    @PrimitiveCollection
    List<String> findPermissionByRoleId(long roleId);

    /**
     * 根据用户id查找该用户所含有的权限标识
     *
     * @param userId
     * @return
     */
    @SQL("SELECT tor.permission_mark AS pm FROM t_oauth_resource tor" +
            " WHERE tor.enabled = 1 AND tor.ID " +
            "  IN (SELECT tom.resource_id FROM t_oauth_menu tom " +
            "WHERE tom.resource_id = tor.ID AND tom.enabled = 1 AND tom.role_id" +
            "  IN (SELECT tour.role_id FROM t_oauth_user_role tour WHERE tour.user_id = ?))")
    @PrimitiveCollection
    List<String> findPermissionByUserId(long userId);

    /**
     * 修改
     *
     * @param resource
     * @return
     */
    @Modify(Router.DEFAULT)
    int updateById(Resource resource);
}
