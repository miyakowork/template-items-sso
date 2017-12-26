package me.wuwenbin.items.sso.service.service;

import me.wuwenbin.items.sso.dao.entity.ShiroSession;
import me.wuwenbin.items.sso.dao.model.queryBo.SessionBo;
import me.wuwenbin.modules.jpa.support.Page;

/**
 * created by Wuwenbin on 2017/12/25 at 19:11
 *
 * @author wuwenbin
 */
public interface ShiroSessionService {

    /**
     * 根据用户名查找session
     *
     * @param page      page对象
     * @param sessionBo 搜索对象
     * @return 查询的页面结果信息
     */
    Page<ShiroSession> findPage(Page<ShiroSession> page, SessionBo sessionBo);

}
