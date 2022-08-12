package code.example.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import code.example.springboot.interceptor.LogInterceptor;
import code.example.springboot.interceptor.RequestInterceptor;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

	private final Logger log = LoggerFactory.getLogger(WebAppConfigurer.class);
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 可添加多个
    	log.info("WebAppConfigurer#addInterceptors");
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
        registry.addWebRequestInterceptor(new RequestInterceptor()).addPathPatterns("/**");
    }
}
