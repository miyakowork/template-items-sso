package me.wuwenbin.items.sso.service.config.filter;

import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import me.wuwenbin.items.sso.service.support.util.HttpUtils;
import me.wuwenbin.items.sso.service.utils.FilterUtils;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * created by Wuwenbin on 2017/8/11 at 19:11
 */
public interface TemplateFilter {

    /**
     * 通用被拒绝时候的处理
     *
     * @param servletRequest
     * @param servletResponse
     * @param ajaxMessage
     * @param loginUrl
     * @throws IOException
     */
    default boolean denyControl(ServletRequest servletRequest, ServletResponse servletResponse, String ajaxMessage, String loginUrl) throws IOException {
        HttpServletRequest request = WebUtils.toHttp(servletRequest);
        HttpServletResponse response = WebUtils.toHttp(servletResponse);
        if (HttpUtils.isAjax(request)) {
            if (HttpUtils.isRouter(request)) {
                WebUtils.issueRedirect(servletRequest, servletResponse, ShiroConsts.LOGIN_ROUTER);
            } else {
                FilterUtils.ajaxControl(response, ajaxMessage);
            }
        } else {
            if (loginUrl == null || "".equals(loginUrl)) {
                loginUrl = ShiroConsts.LOGIN_URL;
            }
            WebUtils.saveRequest(servletRequest);
            WebUtils.issueRedirect(request, response, loginUrl);
        }
        return false;
    }

    /**
     * authc验证器的处理
     *
     * @param servletRequest
     * @param servletResponse
     * @param ajaxMessage
     * @param loginUrl
     * @return
     * @throws IOException
     */
    default boolean denyControlOnFormAuthenticationFilter(ServletRequest servletRequest, ServletResponse servletResponse, String ajaxMessage, String loginUrl) throws IOException {
        HttpServletRequest request = WebUtils.toHttp(servletRequest);
        HttpServletResponse response = WebUtils.toHttp(servletResponse);
        if (HttpUtils.isAjax(request)) {
            if (HttpUtils.isRouter(request)) {
                WebUtils.issueRedirect(servletRequest, servletResponse, ShiroConsts.LOGIN_ROUTER);
                return false;
            } else {
                if (HttpUtils.isAjax(request) && !HttpUtils.isRouter(request)) {
                    FilterUtils.ajaxControl(response, ajaxMessage);
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * 判断url是不是登录页面、登录路由或者事favicon
     *
     * @param uri
     * @return
     */
    default boolean isLoginOrFavicon(String uri) {
        return uri.contains(ShiroConsts.LOGIN_ROUTER) || uri.contains(ShiroConsts.LOGIN_URL) || uri.contains(ShiroConsts.FAVICON);
    }

}
