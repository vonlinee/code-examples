package org.setamv.shardingsphere.starter.config;

import org.setamv.shardingsphere.starter.config.converters.StringToDateConverter;
import org.setamv.shardingsphere.starter.config.converters.StringToLocalDateConverter;
import org.setamv.shardingsphere.starter.config.converters.StringToLocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

@Configuration
public class WebConfig {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    /**
     * 注册 {@link String} 到 {@link java.util.Date} 的转换器
     */
    @PostConstruct
    public void initWebBinding() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new StringToDateConverter());
            genericConversionService.addConverter(new StringToLocalDateConverter());
            genericConversionService.addConverter(new StringToLocalDateTimeConverter());
        }
    }
}
