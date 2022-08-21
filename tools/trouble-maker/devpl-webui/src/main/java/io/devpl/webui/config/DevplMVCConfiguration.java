package io.devpl.webui.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DevplMVCConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DevplMVCConfiguration.class);

    /**
     * 等效于配置: spring.mvc.static-path-pattern=/static/**
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

    }
}
