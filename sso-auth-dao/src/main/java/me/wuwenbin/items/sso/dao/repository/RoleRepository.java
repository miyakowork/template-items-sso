package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.Role;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.ListMap;
import me.wuwenbin.modules.repository.provider.find.annotation.OrderBy;
import me.wuwenbin.modules.repository.provider.find.annotation.Primitive;
import me.wuwenbin.modules.repository.provider.find.annotation.PrimitiveCollection;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * created by Wuwenbin on 2017/12/24 at 13:10
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface RoleRepository extends IPageAndSortRepository<Role, Long>, IBaseCrudRepository<Role, Long> {

    /**
     * 查找所有的角色按照系统代码排序
     *
     * @return
     */
    @SQL("SELECT r.*,sm.name FROM t_oauth_role r LEFT JOIN t_oauth_system_module sm ON sm.system_code = r.system_code")
    @OrderBy("r.system_code")
    @ListMap
    List<Map<String, Object>> findAllRoles();

    /**
     * 通过系统代码查找可用的角色集合
     *
     * @param systemCode
     * @param enabled
     * @return
     */
    List<Role> findBySystemCodeAndEnabled(String systemCode, boolean enabled);

    /**
     * 通过当前的id当做父级id来查找可用的角色记录数
     *
     * @param pId
     * @param enabled
     * @return
     */
    int countByParentIdAndEnabled(Long pId, boolean enabled);

    /**
     * 根据userId查找用户的角色集合信息
     *
     * @param userId
     * @return
     */
    @SQL("SELECT tor.* FROM t_oauth_role tor WHERE tor.id IN" +
            " (SELECT tour.role_id FROM t_oauth_user_role tour WHERE tour.user_id = ?)" +
            " AND tor.enabled = 1")
    List<Role> findRolesByUserId(long userId);

    /**
     * 通过userId查找角色名集合
     *
     * @param userId
     * @return
     */
    @SQL("SELECT tor.name AS role_name FROM t_oauth_role tor WHERE tor.id IN" +
            " (SELECT tour.role_id FROM t_oauth_user_role tour WHERE tour.user_id = ?)" +
            " AND tor.enabled = 1")
    @PrimitiveCollection
    List<String> findRoleNamesByUserId(long userId);


    /**
     * 通过userId和系统代码查找角色名集合
     *
     * @param userId
     * @param systemCode
     * @return
     */
    @SQL("SELECT tor.name AS role_name FROM t_oauth_role tor WHERE tor.id IN" +
            " (SELECT tour.role_id FROM t_oauth_user_role tour WHERE tour.user_id = ?)" +
            " AND tor.enabled = 1 AND system_code = ?")
    @PrimitiveCollection
    List<String> findRoleNamesByUserIdAndSystemCode(long userId, String systemCode);

    /**
     * 根据用户id和系统模块代码查询用户在此系统下的所有角色
     *
     * @param userId
     * @param systemCode
     * @return
     */
    @SQL("SELECT tor.* FROM t_oauth_role tor WHERE tor.id IN" +
            " (SELECT tour.role_id FROM t_oauth_user_role tour WHERE tour.user_id = ? AND tor.enabled = 1)" +
            " AND tor.enabled = 1 AND tor.system_code = ?")
    @OrderBy("tor.id")
    List<Role> findRolesByUserIdAndSystemCode(long userId, String systemCode);

    /**
     * 修改角色
     *
     * @param role
     * @return
     */
    @Modify(Router.DEFAULT)
    int updateById(Role role);

    /**
     * 通过roleId查找systemCode
     *
     * @param roleId
     * @return
     */
    @Primitive
    String findSystemCodeById(long roleId);


}
