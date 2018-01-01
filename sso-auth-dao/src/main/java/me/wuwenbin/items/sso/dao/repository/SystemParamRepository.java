package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.SystemParam;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2017/12/24 at 13:46
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface SystemParamRepository extends IPageAndSortRepository<SystemParam, Long> {

    /**
     * 根据 name查找对应数据
     *
     * @param name
     * @return
     */
    SystemParam findByName(String name);

    /**
     * 修改
     *
     * @param systemParam
     * @return
     */
    @Modify(Router.DEFAULT)
    int updateById(SystemParam systemParam);
}
