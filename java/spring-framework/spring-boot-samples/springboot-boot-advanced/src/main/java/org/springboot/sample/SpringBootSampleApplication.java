package org.springboot.sample;

import javax.annotation.PostConstruct;
import javax.servlet.Servlet;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.sample.datasource.DynamicDataSourceRegister;
import org.springboot.sample.servlet.MyServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.DispatcherServlet;

@EnableAsync // 支持Servlet异步
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement // 支持事务
@Import({
		DynamicDataSourceRegister.class // 注册动态多数据源
})
public class SpringBootSampleApplication extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(SpringBootSampleApplication.class);

	static {
		// disable devtools
		System.setProperty("spring.devtools.restart.enabled", "false");
	}

	@Bean
	public PlatformTransactionManager txManager(DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootSampleApplication.class);
	}

	@PostConstruct
	public void logTest() {
		logger.debug("日志输出测试 Debug");
		logger.trace("日志输出测试 Trace");
		logger.info("日志输出测试 Info");
	}

	/**
	 * 使用代码注册Servlet（不需要@ServletComponentScan注解）
	 * @return
	 * @author SHANHY
	 * @create 2016年1月6日
	 */
	@Bean(name = "test")
	public ServletRegistrationBean<? extends Servlet> servletRegistrationBean() {
		return new ServletRegistrationBean<>(new MyServlet(), "/xs/*");// ServletName默认值为首字母小写，即myServlet
	}

	/**
	 * 修改DispatcherServlet默认配置
	 */
	@Bean
	public ServletRegistrationBean<? extends Servlet> dispatcherRegistration(DispatcherServlet dispatcherServlet) {
		ServletRegistrationBean<? extends Servlet> registration = new ServletRegistrationBean<>(dispatcherServlet);
		registration.getUrlMappings().clear();
		registration.addUrlMappings("*.do");
		registration.addUrlMappings("*.json");
		return registration;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSampleApplication.class, args);
	}
}