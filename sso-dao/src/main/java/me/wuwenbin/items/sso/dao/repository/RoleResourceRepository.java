package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.RoleResource;
import me.wuwenbin.modules.repository.annotation.field.Routers;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.PrimitiveCollection;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static me.wuwenbin.items.sso.dao.constant.SQLRouters.INSERT_ROLE_RESOURCE;

/**
 * created by Wuwenbin on 2017/12/24 at 13:20
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface RoleResourceRepository extends IBaseCrudRepository<RoleResource, Long> {

    /**
     * 根据roleId和enabled状态查找资源id集合
     *
     * @param roleId
     * @param enabled
     * @return
     */
    @PrimitiveCollection
    List<String> findResourceIdByRoleIdAndEnabled(String roleId, boolean enabled);

    /**
     * 插入t_oauth_role_resource表
     *
     * @param paramMap
     * @return
     * @throws Exception
     */
    @Routers(INSERT_ROLE_RESOURCE)
    int saveRoleResource(Collection<Map<String, Object>> paramMap) throws Exception;

    /**
     * 删除角色资源对照记录
     *
     * @param params
     * @throws Exception
     */
    void deleteByRoleIdAndResourceId(Collection<RoleResource> params) throws Exception;
}
