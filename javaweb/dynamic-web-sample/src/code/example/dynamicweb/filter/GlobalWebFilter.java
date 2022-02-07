package code.example.dynamicweb.filter;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

//@WebFilter(displayName="GlobalWebFilter", urlPatterns= {"/*"})
public class GlobalWebFilter implements Filter {

	private static final Logger LOG = Logger.getLogger(GlobalWebFilter.class.getName());
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info("GlobalWebFilter init");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println(request.getRemoteHost());
	}

	@Override
	public void destroy() {
		
	}
}
