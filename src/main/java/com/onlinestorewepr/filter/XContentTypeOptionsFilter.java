package com.onlinestorewepr.filter;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class XContentTypeOptionsFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("X-Content-Type-Options", "nosniff");
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
