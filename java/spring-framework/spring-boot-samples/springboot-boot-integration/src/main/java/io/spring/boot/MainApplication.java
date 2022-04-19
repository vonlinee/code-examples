package io.spring.boot;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import io.spring.boot.common.db.DynamicDataSourceRegistrar;

@EnableAsync //支持Servlet异步
@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class
})
@ServletComponentScan
@EnableTransactionManagement // 支持事务
@Import({DynamicDataSourceRegistrar.class}) // 注册动态多数据源
public class MainApplication extends SpringBootServletInitializer {

    private static final Logger logger = LoggerFactory.getLogger(MainApplication.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(MainApplication.class);
    }

    @PostConstruct
    public void logTest() {
        logger.debug("日志输出测试 Debug");
    }

    static {
    	System.setProperty("spring.devtools.restart.enabled", "false");
    }
    
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}