package io.devpl.spring.web;

import io.devpl.spring.web.mvc.ControllerMethodProcessor;
import io.devpl.spring.web.mvc.RequestInfoMethodArgumentResolver;
import io.devpl.spring.web.mvc.RestHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@ConditionalOnWebApplication
@Configuration(value = "mvc-configuration")
public class WebMVCConfiguration implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new RequestInfoMethodArgumentResolver());
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        converters.add(new RestHttpMessageConverter());
        handlers.add(new ControllerMethodProcessor(converters));
    }
}
