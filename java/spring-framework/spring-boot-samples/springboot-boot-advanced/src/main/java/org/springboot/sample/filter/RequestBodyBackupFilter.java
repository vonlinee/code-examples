package org.springboot.sample.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class RequestBodyBackupFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (BucnWebConstant.ZEXCELMEDIATYPE.toString().equals(httpRequest.getHeader("accept"))) {
			RequestBodyBackHSRtWrapper requestWrapper = new RequestBodyBackHSRtWrapper(httpRequest);
			chain.doFilter(requestWrapper, response);
		} else {
			chain.doFilter(request, response);
		}
	}
}