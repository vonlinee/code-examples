package org.example.springboot.interceptor;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 * 
 * @since created on 2022年7月28日
 */
public class RequestInterceptor implements WebRequestInterceptor {

	@Override
	public void preHandle(WebRequest request) throws Exception {
		System.out.println(request.getContextPath());
	}

	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {
		
	}

	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {
		
	}
}
