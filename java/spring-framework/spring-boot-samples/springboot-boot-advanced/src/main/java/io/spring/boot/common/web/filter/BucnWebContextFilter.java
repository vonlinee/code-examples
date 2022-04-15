package io.spring.boot.common.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: liuweisong
 */
public class BucnWebContextFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String token = httpRequest.getHeader("Authorization");
            if (token == null || token.isEmpty()) {
                token = request.getParameter("token");
            }
            BusicenContext.setToken(token);
            chain.doFilter(request, response);
        } finally {
            BusicenContext.remove();
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
