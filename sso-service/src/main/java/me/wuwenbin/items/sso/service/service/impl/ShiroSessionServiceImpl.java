package me.wuwenbin.items.sso.service.service.impl;

import me.wuwenbin.items.sso.dao.entity.ShiroSession;
import me.wuwenbin.items.sso.dao.model.queryBo.SessionBo;
import me.wuwenbin.items.sso.dao.repository.ShiroSessionRepository;
import me.wuwenbin.items.sso.service.service.ShiroSessionService;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.sql.SQLGen;
import me.wuwenbin.modules.sql.factory.SQLTextBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * created by Wuwenbin on 2017/12/25 at 19:14
 *
 * @author wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ShiroSessionServiceImpl implements ShiroSessionService {

    @Resource
    private ShiroSessionRepository shiroSessionRepository;

    @Override
    public Page<ShiroSession> findPage(Page<ShiroSession> page, SessionBo sessionBo) {
        SQLTextBuilder ssb = SQLGen.builder();
        String sql = ssb.selectAll("t_oauth_session");
        return shiroSessionRepository.findPagination(sql, page, ShiroSession.class, sessionBo);
    }

}
