package me.wuwenbin.items.sso.service.config.filter;

import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import me.wuwenbin.items.sso.service.support.util.ShiroUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * created by Wuwenbin on 2017/8/2 at 20:44
 *
 * @author wuwenbin
 */
public class MyUserFilter extends UserFilter implements TemplateFilter {

    private static final Logger LOG = LoggerFactory.getLogger(MyUserFilter.class);

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        HttpServletRequest req = (HttpServletRequest) request;
        String uri = req.getRequestURI();
        String method = req.getMethod();
        LOG.info("-- MyUserFilter，访问URI：[{}]，请求方式：[{}]", uri, method);

        boolean isRemembered = ShiroUtils.getSubject().isRemembered();
        return isRemembered || super.isAccessAllowed(request, response, mappedValue);
    }

    /**
     * 访问被拒绝就不再执行下一个filter
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return denyControl(servletRequest, servletResponse, "登录过期，请重新登录！", ShiroConsts.LOGIN_URL);
    }

}
