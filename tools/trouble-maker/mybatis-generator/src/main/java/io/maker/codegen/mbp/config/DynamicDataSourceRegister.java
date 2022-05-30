package io.maker.codegen.mbp.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 动态数据源注册 启动动态数据源请在启动类中添加 @Import(DynamicDataSourceRegister.class)
 */
public class DynamicDataSourceRegister implements ImportBeanDefinitionRegistrar, EnvironmentAware {

	private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRegister.class);

	private ConversionService conversionService = new DefaultConversionService();
	
	private PropertyValues dataSourcePropertyValues;

	// 如配置文件中未指定数据源类型，使用该默认值
	public enum DataSourceType {
		TOMCAT_JDBC("org.apache.tomcat.jdbc.pool.DataSource"),
		HIKARI("com.zaxxer.hikari.HikariDataSource"),
		SPRING_MANAGED("org.springframework.jdbc.datasource.DriverManagerDataSource"),
		TOMCAT_DBCP2("org.apache.tomcat.dbcp.dbcp2.managed.BasicManagedDataSource"),
		DRUID("com.alibaba.druid.pool.DruidDataSource");

		String className;

		private DataSourceType(String className) {
			this.className = className;
		}

		public String getClassName() {
			return className;
		}

		public void setClassName(String className) {
			this.className = className;
		}
	}

	// 数据源
	private DataSource defaultDataSource;
	
	private Map<String, DataSource> customDataSources = new HashMap<>();

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<Object, Object> targetDataSources = new HashMap<>();

		// 创建DynamicDataSource
		GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClass(DynamicDataSource.class);
		beanDefinition.setSynthetic(true);
		MutablePropertyValues mpv = beanDefinition.getPropertyValues();
		mpv.addPropertyValue("defaultTargetDataSource", defaultDataSource);
		mpv.addPropertyValue("targetDataSources", targetDataSources);
		registry.registerBeanDefinition("dataSource", beanDefinition);

		logger.info("Dynamic DataSource Registry");
	}

	/**
	 * 创建DataSource
	 */
	@SuppressWarnings("unchecked")
	public DataSource buildDataSource(Map<String, String> dsMap) {
		try {
			Object type = dsMap.get("type");
			if (type == null)
				type = DataSourceType.DRUID.getClassName();// 默认DataSource
			Class<? extends DataSource> dataSourceType;
			dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
			String driverClassName = dsMap.get("driver-class-name").toString();
			String url = dsMap.get("url").toString();
			String username = dsMap.get("username").toString();
			String password = dsMap.get("password").toString();
			DataSourceBuilder<?> factory = DataSourceBuilder.create().driverClassName(driverClassName).url(url)
					.username(username).password(password).type(dataSourceType);
			return factory.build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载多数据源配置
	 */
	@Override
	public void setEnvironment(Environment env) {
		initDefaultDataSource(env);
		initCustomDataSources(env);
	}

	/**
	 * 初始化主数据源
	 *
	 * @author SHANHY
	 * @create 2016年1月24日
	 */
	private void initDefaultDataSource(Environment env) {
		// 读取主数据源
		// SpringBoot 1.5.x版本使用RelaxedPropertyResolver
		// RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env,
		// "spring.datasource.");

		Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(env);
		Binder binder = new Binder(sources);
		BindResult<Properties> bindResult = binder.bind("spring.datasource", Properties.class);
		Properties properties = bindResult.get();

		Map<String, String> dsMap = new HashMap<>();
		dsMap.put("type", properties.getProperty("type"));
		dsMap.put("driver-class-name", properties.getProperty("driver-class-name"));
		dsMap.put("url", properties.getProperty("url"));
		dsMap.put("username", properties.getProperty("username"));
		dsMap.put("password", properties.getProperty("password"));
		defaultDataSource = buildDataSource(dsMap);
		dataBinder(defaultDataSource, env);
	}

	/**
	 * 为DataSource绑定更多数据
	 *
	 * @param dataSource
	 * @param env
	 * @author SHANHY
	 * @create 2016年1月25日
	 */
	private void dataBinder(DataSource dataSource, Environment env) {
		// RelaxedDataBinder dataBinder = new RelaxedDataBinder(dataSource);
		// // dataBinder.setValidator(new
		// // LocalValidatorFactory().run(this.applicationContext));
		// dataBinder.setConversionService(conversionService);
		// dataBinder.setIgnoreNestedProperties(false);// false
		// dataBinder.setIgnoreInvalidFields(false);// false
		// dataBinder.setIgnoreUnknownFields(true);// true
		if (dataSourcePropertyValues == null) {
			// Map<String, Object> rpr = new RelaxedPropertyResolver(env,
			// "spring.datasource").getSubProperties(".");
			// Map<String, Object> values = new HashMap<>(rpr);
			Map<String, Object> values = new HashMap<>();
			// 排除已经设置的属性
			values.remove("type");
			values.remove("driver-class-name");
			values.remove("url");
			values.remove("username");
			values.remove("password");
			dataSourcePropertyValues = new MutablePropertyValues(values);
		}
		// dataBinder.bind(dataSourcePropertyValues);
	}

	/**
	 * 初始化更多数据源
	 *
	 * @author SHANHY
	 * @create 2016年1月24日
	 */
	private void initCustomDataSources(Environment env) {
		// 读取配置文件获取更多数据源，也可以通过defaultDataSource读取数据库获取更多数据源
		// RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(env,
		// "custom.datasource.");
//		String dsPrefixs = propertyResolver.getProperty("names");
//		for (String dsPrefix : dsPrefixs.split(",")) {// 多个数据源
//			Map<String, Object> dsMap = propertyResolver.getSubProperties(dsPrefix + ".");
//			DataSource ds = buildDataSource(dsMap);
//			customDataSources.put(dsPrefix, ds);
//			dataBinder(ds, env);
//		}

		Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(env);
		Binder binder = new Binder(sources);
		BindResult<Properties> bindResult = binder.bind("spring.datasource", Properties.class);
		Properties properties = bindResult.get();

	}
}
