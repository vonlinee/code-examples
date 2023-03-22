package io.devpl.toolkit.startup;

import io.devpl.toolkit.config.props.GeneratorConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.*;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * 不使用@SpringBootApplication和@EnableAutoConfiguration
 * 注解，避免启动时被宿主系统的自动配置所干扰，直接注入需要的配置类
 */
//@SpringBootConfiguration
@Import({DispatcherServletAutoConfiguration.class, ServletWebServerFactoryAutoConfiguration.class, HttpEncodingAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class, MultipartAutoConfiguration.class, ErrorMvcAutoConfiguration.class, WebMvcAutoConfiguration.class})
@ComponentScan("io.devpl.toolkit")
@MapperScan(basePackages = "io.devpl.toolkit.mapper")
public class MybatisPlusToolsApplication {

    public static GeneratorConfig generatorConfig;

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> containerConfig(GeneratorConfig config) {
        return factory -> {
            if (config.getPort() != null) {
                factory.setPort(MybatisPlusToolsApplication.generatorConfig.getPort());
            } else {
                factory.setPort(8080);
            }
            factory.setContextPath("");
        };
    }

    @Bean
    public GeneratorConfig generatorConfig() {
        return MybatisPlusToolsApplication.generatorConfig;
    }
}

