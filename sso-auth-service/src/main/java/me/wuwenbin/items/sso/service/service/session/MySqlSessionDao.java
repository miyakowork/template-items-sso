package me.wuwenbin.items.sso.service.service.session;

import me.wuwenbin.items.sso.dao.entity.ShiroSession;
import me.wuwenbin.items.sso.dao.repository.ShiroSessionRepository;
import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import me.wuwenbin.items.sso.service.support.util.HttpUtils;
import me.wuwenbin.items.sso.service.support.util.SerializableUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;

/**
 * session持久化存储
 *
 * @author Wuwenbin
 * @date 2017/7/19
 */
@Component
public class MySqlSessionDao extends CachingSessionDAO {

    private static final Logger LOG = LoggerFactory.getLogger(MySqlSessionDao.class);

    @Resource
    private ShiroSessionRepository shiroSessionRepository;

    @Override
    protected void doUpdate(Session session) {
        if (session == null || session.getId() == null) {
            LOG.info("session or sessionId is null");
            return;
        }

        if (session instanceof ValidatingSession && !((ValidatingSession) session).isValid()) {
            return; //如果会话过期/停止 没必要再更新了
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (!isStaticFile(request)) {
            ShiroSession shiroSession = new ShiroSession();
            shiroSession.setSessionId(session.getId().toString());
            Object loggedUsername = session.getAttribute(ShiroConsts.SESSION_USERNAME_KEY);
            if (loggedUsername != null) {
                shiroSession.setUsername(loggedUsername.toString());
            } else {
                shiroSession.setUsername(HttpUtils.getRemoteAddr(request));
            }
            shiroSession.setSessionBase64(SerializableUtils.serialize(session));
            shiroSession.setLastVisitDate(session.getLastAccessTime());
            shiroSession.setUpdateUrl(request.getRequestURL().toString());
            shiroSession.setIp(session.getHost());
            shiroSession.setSessionTimeout(session.getTimeout());
            shiroSession.setUserAgent(request.getHeader("User-Agent"));
            try {
                int n = shiroSessionRepository.updateBySessionId(shiroSession);
                LOG.info("更新shiroSession，影响条目:[{}]", n);
            } catch (Exception e) {
                LOG.error("更新shiroSession出现异常，异常信息：", e);
            }
        }
    }

    @Override
    protected void doDelete(Session session) {
        if (session == null || session.getId() == null) {
            LOG.info("session or sessionId is null");
            return;
        }
        try {
            ///删除数据库中
            shiroSessionRepository.deleteBySessionId(session.getId().toString());
        } catch (Exception e) {
            LOG.error("删除shiroSession出现异常，异常信息：", e);
        }
    }

    @Override
    protected Serializable doCreate(Session session) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (isStaticFile(request)) {
            return null;
        }
        //获取自定义 SessionID
        Serializable sessionId = generateSessionId(session);
        //设置SessionID
        assignSessionId(session, sessionId);
//       session.setTimeout(5000);//ShiroConfig中设置了全局的超时时间，此处可单独设置每个的超时间以覆盖全局的
        ShiroSession shiroSession = new ShiroSession();
        shiroSession.setSessionId(session.getId().toString());
        Object loggedUsername = session.getAttribute(ShiroConsts.SESSION_USERNAME_KEY);
        String username;
        if (loggedUsername != null) {
            username = loggedUsername.toString();
        } else {
            username = HttpUtils.getRemoteAddr(request);
        }
        shiroSession.setUsername(username);
        shiroSession.setFirstVisitDate(session.getStartTimestamp());
        shiroSession.setSessionBase64(SerializableUtils.serialize(session));
        shiroSession.setLastVisitDate(session.getLastAccessTime());
        shiroSession.setIp(session.getHost());
        shiroSession.setSessionTimeout(session.getTimeout());
        shiroSession.setCreateUrl(request.getRequestURL().toString());
        shiroSession.setUpdateUrl(request.getRequestURL().toString());
        shiroSession.setUserAgent(request.getHeader("User-Agent"));
        try {
            LOG.info("保存shiroSession", shiroSessionRepository.saveShiroSession(shiroSession));
            return session.getId();
        } catch (Exception e) {
            LOG.error("创建shiroSession出现异常，异常信息：", e);
            return null;
        }


    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        Session session = getCachedSession(sessionId);
        if (session == null) {
            ShiroSession sysSession = shiroSessionRepository.findBySessionId(sessionId.toString());
            if (sysSession != null) {
                session = SerializableUtils.deserialize(sysSession.getSessionBase64());
            }
        }
        return session;
    }


    /**
     * 是否为静态资源文件
     *
     * @param request http请求
     * @return true：是静态文件
     */
    private boolean isStaticFile(HttpServletRequest request) {
        if (request != null) {
            String uri = request.getRequestURI();
            if (uri.contains("/static/")) {
                LOG.info("访问静态资源文件session不做处理");
                return true;
            }
        }
        return false;
    }


    @Override
    public Collection<Session> getActiveSessions() {
        return super.getActiveSessions();
    }
}
