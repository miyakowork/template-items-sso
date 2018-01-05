package me.wuwenbin.items.sso.service.config.shiro;

import me.wuwenbin.modules.utils.lang.LangUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * created by Wuwenbin on 2018/1/4 at 11:05
 *
 * @author wuwenbin
 */
public class MySessionManager extends DefaultWebSessionManager {

    private static final String HEADER_AUTHORIZATION = "authorization::token";
    private static final String PARAM_AUTHORIZATION = "param::token";

    private static final String REFERENCED_SESSION_ID_SOURCE = "stateless::request";

    public MySessionManager() {
        super();
    }

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = WebUtils.toHttp(request);
        String headerToken = req.getHeader(HEADER_AUTHORIZATION);
        String paramToken = req.getParameter(PARAM_AUTHORIZATION);
        String sessionId = LangUtils.string.firstNotEmpty(headerToken, paramToken);
        //如果请求头中有 Authorization 则其值为sessionId
        if (!StringUtils.isEmpty(sessionId)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        } else {
            //否则按默认规则从cookie取sessionId
            return super.getSessionId(request, response);
        }
    }
}
