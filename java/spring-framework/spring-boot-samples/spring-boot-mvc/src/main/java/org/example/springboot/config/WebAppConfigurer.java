package org.example.springboot.config;

import org.example.springboot.interceptor.LogInterceptor;
import org.example.springboot.interceptor.RequestInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

	private final Logger log = LoggerFactory.getLogger(WebAppConfigurer.class);

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // log.info("extendMessageConverters => {}", converters.size());
        // 优先使用自定义的进行处理
        // converters.add(0, new InternalHttpMessageConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
    	log.info("WebAppConfigurer#addInterceptors");
//        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
//        registry.addWebRequestInterceptor(new RequestInterceptor()).addPathPatterns("/**");
    }
}
