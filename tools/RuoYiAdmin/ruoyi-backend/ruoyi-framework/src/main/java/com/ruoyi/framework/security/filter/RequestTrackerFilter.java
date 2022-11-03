package com.ruoyi.framework.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;

import javax.servlet.*;
import java.io.IOException;

/**
 * 优先级最高
 */
public class RequestTrackerFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(RequestTrackerFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        LOG.info("请求开始 {}", request.getRemoteAddr());
        chain.doFilter(request, response);
    }
}