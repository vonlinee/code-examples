package io.devpl.commons.db.jdbc;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(value = { DataSourceAutoConfiguration.class })
public class DataSourceConfiguration {

	@Bean
	public DataSource dataSource() {
		DynamicDataSource dataSource = new DynamicDataSource();
		return dataSource;
	}
}
