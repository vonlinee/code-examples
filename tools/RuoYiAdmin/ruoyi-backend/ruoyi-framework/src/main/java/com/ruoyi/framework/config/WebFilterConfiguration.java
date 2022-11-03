package com.ruoyi.framework.config;

import com.ruoyi.common.filter.RepeatableFilter;
import com.ruoyi.common.filter.XssFilter;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.security.filter.RequestTrackerFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Filter配置
 *
 * @author ruoyi
 */
@Configuration
public class WebFilterConfiguration {

    @Value("${xss.excludes}")
    private String excludes;

    @Value("${xss.urlPatterns}")
    private String urlPatterns;

    /**
     * 注册XSS过滤器
     * @return
     */
    @Bean
    @ConditionalOnProperty(value = "xss.enabled", havingValue = "true")
    public FilterRegistrationBean<Filter> xssFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(StringUtils.split(urlPatterns, ","));
        registration.setName("xssFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("excludes", excludes);
        registration.setInitParameters(initParameters);
        return registration;
    }

    /**
     * 重复请求过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean<Filter> someFilterRegistration() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RepeatableFilter());
        registration.addUrlPatterns("/*");
        registration.setName("repeatableFilter");
        registration.setOrder(FilterRegistrationBean.LOWEST_PRECEDENCE);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<Filter> requestTrackerFilter() {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestTrackerFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestTrackerFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }
}
