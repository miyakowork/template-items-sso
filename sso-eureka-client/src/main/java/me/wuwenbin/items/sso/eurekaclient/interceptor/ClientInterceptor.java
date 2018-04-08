package me.wuwenbin.items.sso.eurekaclient.interceptor;

import jodd.json.JsonSerializer;
import me.wuwenbin.items.sso.eurekaclient.config.ClientSettings;
import me.wuwenbin.items.sso.eurekaclient.context.ClientCode;
import me.wuwenbin.items.sso.eurekaclient.context.ClientRepository;
import me.wuwenbin.items.sso.eurekaclient.cookie.ClientCookieUtils;
import me.wuwenbin.items.sso.eurekaclient.service.ClientService;
import me.wuwenbin.modules.utils.http.R;
import me.wuwenbin.modules.utils.http.WebUtils;
import me.wuwenbin.modules.utils.lang.LangUtils;
import me.wuwenbin.modules.utils.security.Encrypt;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * created by Wuwenbin on 2018/1/4 at 23:09
 *
 * @author wuwenbin
 */
public class ClientInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(ClientInterceptor.class);

    private ClientSettings client;
    private ClientService clientService;

    public ClientInterceptor(ApplicationContext applicationContext) {
        this.client = applicationContext.getBean(ClientSettings.class);
        this.clientService = applicationContext.getBean(ClientService.class);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //首先判断客户端有没有sessionIdCookie，如果没有就new
        Cookie cookie = ClientCookieUtils.getCookie(request, client.getSessionIdCookie());
        if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
            String sessionIdCookie = Encrypt.digest.md5Hex(LangUtils.random.uuid());
            ClientCookieUtils.setCookie(response, client.getSessionIdCookie(), WebUtils.getRemoteAddr(request), sessionIdCookie, client.getExpire());
            response.sendRedirect(request.getRequestURI());
            return false;
        }

        Method method = ((HandlerMethod) o).getMethod();
        logger.info("====== 当前方法：[{}]", method.getName());
        if (method.isAnnotationPresent(RequiresPermissions.class)) {
            String sessionId = ClientCookieUtils.getCookie(request, client.getSessionIdCookie()).getValue();
            String accessToken = ClientRepository.get(sessionId);
            String[] permissionMarks = method.getAnnotation(RequiresPermissions.class).value();
            Logical logical = method.getAnnotation(RequiresPermissions.class).logical();
            if (!StringUtils.isEmpty(accessToken)) {
                R r = clientService.isPermissionAuthenticated(permissionMarks, accessToken, logical);
                int code = Integer.valueOf(r.get(R.CODE).toString());
                return handleAuthentication(request, response, r, code);
            } else {
                return responseError(request, response, ClientCode.SESSION_TIMEOUT, "未登录或者登录超时，请登录之后再操作！");
            }
        } else if (method.isAnnotationPresent(RequiresRoles.class)) {
            String sessionId = ClientCookieUtils.getCookie(request, client.getSessionIdCookie()).getValue();
            String accessToken = ClientRepository.get(sessionId);
            String[] permissionMarks = method.getAnnotation(RequiresPermissions.class).value();
            Logical logical = method.getAnnotation(RequiresPermissions.class).logical();
            if (!StringUtils.isEmpty(accessToken)) {
                R r = clientService.isRoleAuthenticated(permissionMarks, client.getSystemCode(), accessToken, logical);
                int code = Integer.valueOf(r.get(R.CODE).toString());
                return handleAuthentication(request, response, r, code);
            } else {
                return responseError(request, response, ClientCode.SESSION_TIMEOUT, "未登录或者登录超时，请登录之后再操作！");
            }
        } else {
            return true;
        }
    }

    private boolean handleAuthentication(HttpServletRequest request, HttpServletResponse response, R r, int code) throws IOException {
        if (code == R.SUCCESS) {
            logger.info("====== 验权成功，放行...");
            return true;
        } else if (code == ClientCode.CALL_FAILURE) {
            logger.error("====== " + r.get(R.MESSAGE));
            responseError(request, response, ClientCode.CALL_FAILURE, r.get(R.MESSAGE));
            return false;
        } else if (code == ClientCode.SESSION_TIMEOUT) {
            logger.error("====== " + r.get(R.MESSAGE));
            String key = ClientCookieUtils.getCookie(request, client.getSessionIdCookie()).getValue();
            ClientRepository.delete(key);
            responseError(request, response, ClientCode.SESSION_TIMEOUT, r.get(R.MESSAGE));
            return false;
        } else if (code == ClientCode.AUTH_FAILURE) {
            logger.error("====== " + r.get(R.MESSAGE));
            responseError(request, response, ClientCode.AUTH_FAILURE, r.get(R.MESSAGE));
            return false;
        } else {
            logger.error("====== " + r.get(R.MESSAGE));
            String key = ClientCookieUtils.getCookie(request, client.getSessionIdCookie()).getValue();
            ClientRepository.delete(key);
            responseError(request, response, R.SERVER_ERROR, r.get(R.MESSAGE));
            return false;
        }
    }

    private boolean responseError(HttpServletRequest request, HttpServletResponse response, int code, Object message) throws IOException {
        if (WebUtils.isAjaxRequest(request)) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = response.getWriter();
            R r = R.custom(code, message.toString());
            String json = JsonSerializer.create().include("code", "message", "data").serialize(r);
            out.println(json);
            out.flush();
            out.close();
            return false;
        } else {
            response.sendRedirect(client.getLoginUrl());
            return true;
        }

    }


}
