package org.springboot.sample.filter;

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
}
