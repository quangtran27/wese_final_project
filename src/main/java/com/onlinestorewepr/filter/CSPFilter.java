package com.onlinestorewepr.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CSPFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Content-Security-Policy", "default-src 'self' " +
                "https://fonts.googleapis.com/ https://s3-us-west-2.amazonaws.com/ https://fonts.gstatic.com/ ;" +
                "script-src 'self' ./assets/js/;frame-ancestors 'none';"+
                "style-src 'self' 'unsafe-inline';");
        chain.doFilter(request, response);
    }
    @Override
    public void destroy() {
    }
}
