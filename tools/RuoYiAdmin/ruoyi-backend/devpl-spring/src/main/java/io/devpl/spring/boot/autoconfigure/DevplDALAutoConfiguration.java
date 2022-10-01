package io.devpl.spring.boot.autoconfigure;

import io.devpl.spring.data.jdbc.DataSourceConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * 数据访问层自动配置类
 */
@Configuration
@ConditionalOnClass({DataSource.class})
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        H2ConsoleAutoConfiguration.class
})
@Import(DataSourceConfiguration.class)
public class DevplDALAutoConfiguration {

}
