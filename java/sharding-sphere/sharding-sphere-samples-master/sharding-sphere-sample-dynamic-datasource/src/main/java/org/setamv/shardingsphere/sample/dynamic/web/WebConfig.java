package org.setamv.shardingsphere.sample.dynamic.web;

import org.setamv.shardingsphere.sample.dynamic.web.converters.StringToDateConverter;
import org.setamv.shardingsphere.sample.dynamic.web.converters.StringToLocalDateConverter;
import org.setamv.shardingsphere.sample.dynamic.web.converters.StringToLocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;

/**
 * 增加Web层的数据转换
 *
 * @author setamv
 * @date 20210415
 */
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
        if (initializer != null && initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            if (genericConversionService != null) {
                genericConversionService.addConverter(new StringToDateConverter());
                genericConversionService.addConverter(new StringToLocalDateConverter());
                genericConversionService.addConverter(new StringToLocalDateTimeConverter());
            }
        }
    }
}
