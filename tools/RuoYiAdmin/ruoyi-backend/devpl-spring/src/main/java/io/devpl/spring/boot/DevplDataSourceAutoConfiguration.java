package io.devpl.spring.boot;

import io.devpl.spring.data.jdbc.DataSourceConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * 功能描述：rabbitmq自动配置类
 */
@Configuration
@ConditionalOnClass({DataSource.class})
@Import(DataSourceConfiguration.class)
public class DevplDataSourceAutoConfiguration {

}
