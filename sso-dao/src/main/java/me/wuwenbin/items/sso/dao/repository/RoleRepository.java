package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.Role;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.OrderBy;
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
public interface RoleRepository extends IPageAndSortRepository<Role, Long> {

    /**
     * 查找所有的角色按照系统代码排序
     *
     * @return
     */
    @SQL("SELECT r.*,sm.name FROM t_oauth_role r LEFT JOIN t_oauth_system_module sm ON sm.system_code = r.system_code")
    @OrderBy("r.system_code")
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
}
