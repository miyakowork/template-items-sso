package me.wuwenbin.items.sso.eurekaclient.interceptor;

import me.wuwenbin.items.sso.eurekaclient.PermissionIdentity;
import me.wuwenbin.items.sso.eurekaclient.context.GlobalRepository;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * created by Wuwenbin on 2018/1/4 at 23:09
 *
 * @author wuwenbin
 */
@Component
public class ClientInterceptor extends HandlerInterceptorAdapter {

    @Value("${template.client.login-url}")
    private String loginUrl;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        System.out.println("=======preHandle==========");
        Method method = ((HandlerMethod) o).getMethod();
        System.out.println(method.getName());
        if (method.isAnnotationPresent(RequiresPermissions.class)) {
            String[] permissionMarks = method.getAnnotation(RequiresPermissions.class).value();
            Logical logical = method.getAnnotation(RequiresPermissions.class).logical();
            String accessToken = GlobalRepository.get(GlobalRepository.ACCESS_TOKEN);
            if (StringUtils.isEmpty(accessToken)) {
                response.sendRedirect(loginUrl);
                return false;
            } else {
                //TODO:权限判断有多个并且还要区分逻辑
                if (!PermissionIdentity.isAuthenticated(accessToken, permissionMarks[0])) {
                    response.sendRedirect(loginUrl);
                    return false;
                }
            }
        }
        return true;
    }

}
