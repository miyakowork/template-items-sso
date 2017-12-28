package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.ShiroSession;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.save.annotation.SaveSQL;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import org.springframework.transaction.annotation.Transactional;

import static me.wuwenbin.items.sso.dao.constant.SQLRouters.UPDATE_SESSION;

/**
 * created by Wuwenbin on 2017/12/25 at 14:30
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface ShiroSessionRepository extends IPageAndSortRepository<ShiroSession, Long> {

    /**
     * 通过sessionId查找ShiroSession对象
     *
     * @param sessionId
     * @return
     */
    ShiroSession findBySessionId(String sessionId);

    /**
     * 刷新会话记录时间
     *
     * @param shiroSession
     * @return
     * @throws Exception
     */
    @Modify(UPDATE_SESSION)
    int updateBySessionId(ShiroSession shiroSession) throws Exception;

    /**
     * 根据sessionId删除对应的 session
     *
     * @param sessionId
     * @throws Exception
     */
    void deleteBySessionId(String sessionId) throws Exception;

    /**
     * 插入新的会话记录
     *
     * @param shiroSession
     * @return
     * @throws Exception
     */
    @SaveSQL(columns = {"username", "session_id", "ip", "session_base64", "session_timeout", "create_url", "update_url", "user_agent", "first_visit_date", "last_visit_date"})
    int saveShiroSession(ShiroSession shiroSession) throws Exception;
}
