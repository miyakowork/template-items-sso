package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.Resource;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * created by Wuwenbin on 2017/12/24 at 13:36
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface ResourceRepository extends IPageAndSortRepository<Resource,Long> {

}
