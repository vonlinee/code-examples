package io.maker.common.spring.cloud;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@ConditionalOnClass({ RequestInterceptor.class })
public class FeignRequestInterceptor implements RequestInterceptor {
	
	@Override
	public void apply(RequestTemplate requestTemplate) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attributes != null) {
			HttpServletRequest request = attributes.getRequest();
			String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (auth != null && !auth.isEmpty()) {
				requestTemplate.header(HttpHeaders.AUTHORIZATION, new String[] { auth });
			}
		}
	}
}