package com.onlinestorewepr.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@WebFilter(urlPatterns = {"/*","/web/*"})
public class SameSiteCookieFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        addSameSiteAttribute((HttpServletResponse) response);
    }
    private void addSameSiteAttribute(HttpServletResponse response) {
        Collection<String> headers = response.getHeaders("Set-Cookie");
        boolean firstHeader = true;
        for (String header : headers) {
            if (firstHeader) {
                response.setHeader("Set-Cookie", String.format("%s; %s",
                        header, "SameSite=Strict"));
                firstHeader = false;
                continue;
            }
            response.addHeader("Set-Cookie", String.format("%s; %s", header,
                    "SameSite=Strict"));
        }
    }
}
