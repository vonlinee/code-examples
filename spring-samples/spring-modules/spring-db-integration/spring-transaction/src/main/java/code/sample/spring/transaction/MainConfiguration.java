package code.sample.spring.transaction;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import code.sample.spring.transaction.declaratively.annotation.DataSourceConfiguration;

@Configuration
@Import(DataSourceConfiguration.class)
@ComponentScan("code.sample.spring.transaction")
@ImportResource("classpath:spring-bean.xml")
public class MainConfiguration {

	
}
