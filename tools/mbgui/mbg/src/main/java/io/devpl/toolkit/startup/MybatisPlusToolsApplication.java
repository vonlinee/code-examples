package io.devpl.toolkit.startup;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.*;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

/**
 * 不使用@SpringBootApplication和@EnableAutoConfiguration
 * 注解，避免启动时被宿主系统的自动配置所干扰，直接注入需要的配置类
 */
@SpringBootConfiguration
// @Import({DispatcherServletAutoConfiguration.class, ServletWebServerFactoryAutoConfiguration.class, HttpEncodingAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class, MultipartAutoConfiguration.class, ErrorMvcAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class MybatisPlusToolsApplication {

    // @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerConfig() {
        return factory -> {
            factory.setPort(8068);
            factory.setContextPath("");
        };
    }
}

