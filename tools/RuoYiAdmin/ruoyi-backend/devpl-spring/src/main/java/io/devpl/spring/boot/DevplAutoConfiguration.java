package io.devpl.spring.boot;

import io.devpl.spring.context.DevplProperties;
import io.devpl.spring.context.DevplPropertyFactory;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "devpl.enable", havingValue = "true")
@EnableConfigurationProperties(value = {DevplProperties.class})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,      // 移除默认的数据源配置
        HibernateJpaAutoConfiguration.class,    // 移除默认的JPA配置
        JdbcTemplateAutoConfiguration.class     // 移除默认的JdbcTemplate配置
})
@PropertySource(value = {"classpath:devpl.properties"}, encoding = "UTF-8", factory = DevplPropertyFactory.class)
@AutoConfigureOrder(value = Integer.MIN_VALUE)
public class DevplAutoConfiguration {

}
