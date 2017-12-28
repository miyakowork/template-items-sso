package me.wuwenbin.items.sso.service.config.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Xss过滤
 *
 * @author wuwenbin
 * @date 2017/5/23
 */
public class XssFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
                (HttpServletRequest) request);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {
    }

    /**
     * xss过滤处理
     */
    static class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {
        /**
         * 没被包装过的HttpServletRequest（特殊场景，需求自己过滤）
         */
        HttpServletRequest orgRequest;
        /**
         * html过滤
         */
        private final static HTMLFilter HTML_FILTER = new HTMLFilter();

        XssHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            orgRequest = request;
        }

        @Override
        public String getParameter(String name) {
            String value = super.getParameter(xssEncode(name));
            if (isNotBlank(value)) {
                value = xssEncode(value);
            }
            return value;
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] parameters = super.getParameterValues(name);
            if (parameters == null || parameters.length == 0) {
                return null;
            }

            for (int i = 0; i < parameters.length; i++) {
                parameters[i] = xssEncode(parameters[i]);
            }
            return parameters;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            Map<String, String[]> map = new LinkedHashMap<>();
            Map<String, String[]> parameters = super.getParameterMap();
            for (String key : parameters.keySet()) {
                String[] values = parameters.get(key);
                for (int i = 0; i < values.length; i++) {
                    values[i] = xssEncode(values[i]);
                }
                map.put(key, values);
            }
            return map;
        }

        @Override
        public String getHeader(String name) {
            String value = super.getHeader(xssEncode(name));
            if (isNotBlank(value)) {
                value = xssEncode(value);
            }
            return value;
        }

        private String xssEncode(String input) {
            return HTML_FILTER.filter(input);
        }

        /**
         * 获取最原始的request
         */
        public HttpServletRequest getOrgRequest() {
            return orgRequest;
        }

        /**
         * 获取最原始的request
         */
        public static HttpServletRequest getOrgRequest(HttpServletRequest request) {
            if (request instanceof XssHttpServletRequestWrapper) {
                return ((XssHttpServletRequestWrapper) request).getOrgRequest();
            }

            return request;
        }

    }

    private static boolean isNotBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return false;
        }
        for (int i = 0; i < strLen; i++) {
            if ((!Character.isWhitespace(str.charAt(i)))) {
                return true;
            }
        }
        return false;
    }
}
