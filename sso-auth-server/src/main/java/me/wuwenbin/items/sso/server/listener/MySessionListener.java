package me.wuwenbin.items.sso.server.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListenerAdapter;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * created by Wuwenbin on 2018/1/1 at 16:56
 *
 * @author wuwenbin
 */
@Component
public class MySessionListener extends SessionListenerAdapter {

    private static Logger LOG = LoggerFactory.getLogger(MySessionListener.class);

    @Autowired
    private RedisSessionDAO redisSessionDAO;

    @Override
    public void onStop(Session session) {
        LOG.info("==============会话注销=============");
        redisSessionDAO.delete(session);
    }

    @Override
    public void onExpiration(Session session) {
        LOG.info("==============会话过期=============");
        redisSessionDAO.delete(session);
    }
}
