package sample.dynamic.datasource;

import static org.assertj.core.api.Assertions.contentOf;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication(exclude= {
//		DataSourceAutoConfiguration.class,
//		JdbcTemplateAutoConfiguration.class,
//		TransactionAutoConfiguration.class
//})
@SpringBootApplication
@MapperScan("sample.dynamic.datasource.mapper")
@EnableAspectJAutoProxy(exposeProxy = true) // 启动AOP
@EnableTransactionManagement
public class DynamicDatasourceApplication {
	public static void main(String[] args) {
		SpringApplication.run(DynamicDatasourceApplication.class, args);
	}
}
