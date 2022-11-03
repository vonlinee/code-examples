package io.devpl.auth.config;

import io.devpl.auth.core.BasicAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import javax.servlet.Filter;

@Configuration
public class WebAuthConfiguration {

    @Bean
    public FilterRegistrationBean<Filter> authFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.addUrlPatterns("/*");
        bean.setFilter(new BasicAuthFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
