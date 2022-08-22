package io.devpl.webui.config;

import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class DevplMVCConfiguration implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(DevplMVCConfiguration.class);

    /**
     * 等效于配置: spring.mvc.static-path-pattern=/static/**
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

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 优先级最高

        System.out.println("内部支持的HttpMessageConverter如下：");
        for (HttpMessageConverter<?> converter : converters) {
            System.out.println(converter);
        }
        converters.add(0, new DevplMessageConverter());
        // converters.add(0, new FastJsonHttpMessageConverter());
    }
}
