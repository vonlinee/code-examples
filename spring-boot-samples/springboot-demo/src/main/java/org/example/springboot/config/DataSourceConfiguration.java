package org.example.springboot.config;

import java.util.Properties;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.example.springboot.config.properties.DruidProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
// @PropertySource(value = { "classpath:/jdbc.properties" })
@EnableTransactionManagement(order = 2) // 需要引入springboot-data-jpa依赖
public class DataSourceConfiguration {

	@Resource
	private DruidProperties druidProperties; // 通过代码配置的属性类

	/**
	 * 单数据源连接池配置 DruidDataSource对Druid版本有要求，>= 1.1.2，可能不准确
	 */
	@Bean
	public DataSource druidDatasource() {
		DruidDataSource dataSource = new DruidDataSource();
		druidProperties.config(dataSource);
		return dataSource;
	}

//	// 注入第一个数据源，生成sessionFactory
//	@Bean("sessionFactory")
//	@Autowired
//	@Primary
//	public LocalSessionFactoryBean getSessionFactory(@Qualifier("druidDatasource") DataSource dataSource) {
//		return buildLocalSessionFactory(dataSource);
//	}

	// 原文链接：https://blog.csdn.net/u011930054/article/details/106856750

	/**
	 * 设置Hibernate的配置属性
	 * @return
	 */
	private Properties getHibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQL55Dialect");
		hibernateProperties.put("current_session_context_class",
				"org.springframework.orm.hibernate5.SpringSessionContext");
		hibernateProperties.put("hibernate.show_sql", "true");
		hibernateProperties.put("hibernate.format_sql", "false");
		hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
		return hibernateProperties;
	}
	
	//原文链接：https://blog.csdn.net/f4761/article/details/83831584
	/**
	 * 构建LocalSessionFactoryBean实例,
	 * org.springframework.orm.hibernate5.LocalSessionFactoryBean
	 * @param dataSource 构建实例所使用的的数据源
	 * @return
	 */
	private LocalSessionFactoryBean buildLocalSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();
		localSessionFactoryBean.setDataSource(dataSource); // 配置数据源,指定成第一个数据源
		// 如果使用 xml 配置则使用该方法进行包扫描
		// PathMatchingResourcePatternResolver pmprpr = new
		// PathMatchingResourcePatternResolver();
		// Resource[] resource =
		// pmprpr.getResources("classpath*:com/ml/hibernatepro/ml/domain/*.hbm.xml");
		// localSessionFactoryBean.setMappingLocations(resource);
		// 现在配置基本都切换到 java config
		// localSessionFactoryBean.setAnnotatedPackages("classpath*:com/ml/hibernatepro/ml/domain");
		// 添加 Hibernate 配置规则
		localSessionFactoryBean.setHibernateProperties(getHibernateProperties());
		// 指定需要扫描的hibernate的Entity实体类包名，可以指定多个包名
		localSessionFactoryBean.setPackagesToScan("org.example.springboot.transaction");
		return localSessionFactoryBean;
	}

	// 注入第二个数据源生成secondSessionFactory
//	@Autowired
//	@Bean("secondSessionFactory")
//	public LocalSessionFactoryBean getSecondSessionFactory(@Qualifier("secondDataSource") DataSource dataSource) {
//		return buildLocalSessionFactory(dataSource);
//	}

	// 原文链接：https://blog.csdn.net/u011930054/article/details/106856750
	// // 第一个数据源
	// @Bean(name = "firstDataSource")
	// @ConfigurationProperties(prefix = "app.datasource.first") //
	// application.properties文件中前缀配置引用
	// @Primary // 多个数据源时首先注入
	// public DataSource firstDataSource() {
	// return DataSourceBuilder.create().build();
	// }
	//
	// // 第二个数据源
	// @Bean(name = "secondDataSource")
	// @ConfigurationProperties(prefix = "app.datasource.second") //
	// application.properties文件中前缀配置引用
	// public DataSource secondDataSource() {
	// return DataSourceBuilder.create().build();
	// }

	// 通过配置文件配置，再由@Value注解注入配置到类的属性中
	// @Value("${jdbc.driverClassName}")
	// private String driverClassName;
	//
	// @Value("${jdbc.url}")
	// private String url;
	//
	// @Value("${jdbc.username}")
	// private String username;
	//
	// @Value("${jdbc.password}")
	// private String password;
	//
	// @Value("${jdbc.initialSize}")
	// private int initialSize;
	//
	// @Value("${jdbc.maxActive}")
	// private int maxActive;
	//
	// @Value("${jdbc.minIdle}")
	// private int minIdle;
	//
	// @Value("${jdbc.maxWait}")
	// private long maxWait;
	//
	// @Value("${jdbc.removeAbandoned}")
	// private boolean removeAbandoned;
	//
	// @Value("${jdbc.removeAbandonedTimeout}")
	// private int removeAbandonedTimeout;
	//
	// @Value("${jdbc.timeBetweenEvictionRunsMillis}")
	// private long timeBetweenEvictionRunsMillis;
	//
	// @Value("${jdbc.minEvictableIdleTimeMillis}")
	// private long minEvictableIdleTimeMillis;
	//
	// @Value("${jdbc.validationQuery}")
	// private String validationQuery;
	//
	// @Value("${jdbc.testWhileIdle}")
	// private boolean testWhileIdle;
	//
	// @Value("${jdbc.testOnBorrow}")
	// private boolean testOnBorrow;
	//
	// @Value("${jdbc.testOnReturn}")
	// private boolean testOnReturn;
	//
	// @Value("${jdbc.poolPreparedStatements}")
	// private boolean poolPreparedStatements;
	//
	// @Value("${jdbc.maxPoolPreparedStatementPerConnectionSize}")
	// private int maxPoolPreparedStatementPerConnectionSize;

	// @Bean
	// @ConditionalOnMissingBean
	// public DataSource druidDataSource1() {
	// DruidDataSource druidDataSource = new DruidDataSource();
	// return druidDataSource;
	// }

	// @Bean(initMethod = "init", destroyMethod = "close")
	// public DataSource druidDataSource() {
	// DruidDataSource ds = new DruidDataSource();
	// ds.setDriverClassName(driverClassName);
	// ds.setUrl(url);
	// ds.setUsername(username);
	// ds.setPassword(password);
	// ds.setInitialSize(initialSize);
	// ds.setMaxActive(maxActive);
	// ds.setMinIdle(minIdle);
	// ds.setMaxWait(maxWait);
	// ds.setRemoveAbandoned(removeAbandoned);
	// ds.setRemoveAbandonedTimeout(removeAbandonedTimeout);
	// ds.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
	// ds.setValidationQuery(validationQuery);
	// ds.setTestWhileIdle(testWhileIdle);
	// ds.setTestOnBorrow(testOnBorrow);
	// ds.setTestOnReturn(testOnReturn);
	// ds.setPoolPreparedStatements(poolPreparedStatements);
	// ds.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
	// return ds;
	// }
}
