package me.wuwenbin.items.sso.eurekaclient.interceptor;

import me.wuwenbin.items.sso.eurekaclient.config.ClientSettings;
import me.wuwenbin.items.sso.eurekaclient.context.ClientRepository;
import me.wuwenbin.items.sso.eurekaclient.cookie.ClientCookieUtils;
import me.wuwenbin.modules.utils.security.Encrypt;
import me.wuwenbin.modules.utils.security.asymmetric.KeyType;
import me.wuwenbin.modules.utils.security.asymmetric.RSA;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * created by Wuwenbin on 2018/1/7 at 19:40
 *
 * @author wuwenbin
 */
public class ClientIndexInterceptor extends HandlerInterceptorAdapter {

    private ClientSettings client;

    public ClientIndexInterceptor(ApplicationContext applicationContext) {
        this.client = applicationContext.getBean(ClientSettings.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String sessionId = ClientCookieUtils.getCookie(request, client.getSessionIdCookie()).getValue();
        String encryptAccessToken = request.getParameter("access_token");
        if (!StringUtils.isEmpty(encryptAccessToken)) {
            RSA rsa = new RSA(Encrypt.base64.decode(client.getPrivateKey()), null);
            String accessToken = rsa.decryptStr(encryptAccessToken, KeyType.PrivateKey);
            ClientRepository.put(sessionId, accessToken);
        }
        return true;
    }
}
