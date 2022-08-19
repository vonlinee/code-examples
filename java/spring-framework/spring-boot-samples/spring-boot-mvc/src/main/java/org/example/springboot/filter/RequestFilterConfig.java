package org.example.springboot.filter;

import org.example.springboot.utils.InternalLogger;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author baishengwen
 * @version 1.0
 * @date 2021/7/28 20:29
 */
@Configuration
public class RequestFilterConfig {
    @Bean
    public FilterRegistrationBean<Filter> filterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        HttpTracerFilter filter = new HttpTracerFilter();
        registration.setFilter(filter);
        List<String> urlList = new ArrayList<>();
        urlList.add("/*");
        registration.setUrlPatterns(urlList);
        registration.setName("UrlFilter");
        registration.setOrder(1);  // 优先级较高

        InternalLogger.debug("add Filter => {}", filter);
        return registration;
    }
}
