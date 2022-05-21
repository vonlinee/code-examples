package io.maker.codegen.mbp.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import io.maker.codegen.core.db.JdbcUtils;

@Configuration
public class DataSourceConfiguration {

	@Bean(name = "DS_informationSchema")
	public DataSource dataSource() {
		Properties properties = JdbcUtils.getProperties("C:\\Users\\ly-wangliang\\Desktop\\code-samples\\tools\\trouble-maker\\mybatis-generator\\src\\main\\resources\\druid.jdbc.properties");
		try {
			return DruidDataSourceFactory.createDataSource(properties);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
