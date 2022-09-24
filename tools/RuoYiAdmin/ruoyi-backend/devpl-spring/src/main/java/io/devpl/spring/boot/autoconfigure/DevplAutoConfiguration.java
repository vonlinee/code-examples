package io.devpl.spring.boot.autoconfigure;

import io.devpl.spring.context.DevplPropertyFactory;
import io.devpl.spring.context.properties.DevplProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * Devpl-Spring所有自动配置的入口
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "devpl.enable", havingValue = "true")
@EnableConfigurationProperties(value = {DevplProperties.class})
@EnableAutoConfiguration
@PropertySource(value = {
        "classpath:devpl*.properties", "classpath:devpl*.yml", "classpath:devpl*.xml"
}, encoding = "UTF-8", factory = DevplPropertyFactory.class, ignoreResourceNotFound = false)
@ImportResource(locations = {
        "classpath:devpl-spring.xml"
})
@ConfigurationPropertiesScan(basePackages = "io.devpl.spring.context.properties")
@Import(DataSourceAutoConfiguration.class)
public class DevplAutoConfiguration {

}
