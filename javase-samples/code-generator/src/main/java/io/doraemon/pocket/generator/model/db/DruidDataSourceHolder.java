package io.doraemon.pocket.generator.model.db;

import java.util.Properties;

import javax.sql.DataSource;
import com.alibaba.druid.pool.DruidDataSource;
import io.doraemon.pocket.generator.utils.DataSourceFactory;

public class DruidDataSourceHolder implements DataSourceFactory {

	private DataSource dataSource;

	@Override
	public void setProperties(Properties props) {
//		DruidDataSource dataSource = new DruidDataSource();
//		// dataSource.setDriverClassName(driverClassName);//如果不配置druid会根据url自动识别dbType，然后选择相应的driverClassName
//		dataSource.setUrl();
//		dataSource.setUsername(username);
//		dataSource.setPassword(password);
//		dataSource.setValidationQuery("SELECT 1");// 用来检测连接是否有效
//		dataSource.setTestOnBorrow(false);// 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
//		dataSource.setTestOnReturn(false);// 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
//		// 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
//		dataSource.setTestWhileIdle(true);// 如果检测失败，则连接将被从池中去除
//		dataSource.setTimeBetweenEvictionRunsMillis(600000);
//		dataSource.setMaxActive(20);
//		dataSource.setInitialSize(10);
//		
//		return dataSource;
	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}
}
