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
 * 此处的sessionManager重写了DefaultWenSessionManager默认的getSessionId方法
 * 可以从url参数或者请求头中获取sessionId，以此来打到第三方app访问的目的
 * 但是由于此处为auth-server所以不提供，需要支持第三方apps的access_token访问的请在第三方的sso-app-*中自行添加相应的操作（非此处的shiro操作，此处只是做一个示例）
 * 通过access_token访问sso-app-*，继而通过sso-app-*来访问sso-auth-server来获取相应资源
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
