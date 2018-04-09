package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.SystemModule;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IBaseCrudRepository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.find.annotation.Primitive;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/24 at 13:43
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface SystemModuleRepository extends IPageAndSortRepository<SystemModule, Long>, IBaseCrudRepository<SystemModule, Long> {

    /**
     * 统计系统代码为systemCode的记录数
     *
     * @param systemCode
     * @return
     */
    int countBySystemCode(String systemCode);

    /**
     * 查询系统模块集合，用户可登录的
     *
     * @param username
     * @return
     */
    @SQL("SELECT DISTINCT m.* FROM t_oauth_system_module m WHERE system_code " +
            "IN(SELECT DISTINCT system_code FROM t_oauth_role WHERE  id " +
            "IN(SELECT role_id FROM t_oauth_user_role ur,t_oauth_user u WHERE u.id = ur.user_id AND u.username = ?))")
    List<SystemModule> findByUserCanLogin(String username);

    /**
     * 查找可用的系统模块
     *
     * @param enabled
     * @return
     */
    List<SystemModule> findByEnabled(boolean enabled);

    /**
     * 修改
     *
     * @param systemModule
     * @return
     */
    @Modify(Router.DEFAULT)
    int updateById(SystemModule systemModule);

    /**
     * 插入非对称密钥
     *
     * @param systemModule
     * @return
     */
    @Modify(Router.A)
    int updateBySystemCode(SystemModule systemModule);

    /**
     * 根据systemCode查找对应的publicKey
     *
     * @param systemCode
     * @return
     */
    @Primitive
    String findPublicKeyBySystemCode(String systemCode);

    /**
     * 根据systemCode查找首页url
     *
     * @param systemCode
     * @return
     */
    @Primitive
    String findIndexUrlBySystemCode(String systemCode);
}
