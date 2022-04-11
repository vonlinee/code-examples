package sample.spring.transaction.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

import sample.spring.transaction.solution.declaratively.annotation.DataSourceConfiguration;

@Configuration
@Import(DataSourceConfiguration.class)
@ComponentScan("sample.spring.transaction")
@ImportResource("classpath:spring-bean.xml")
public class MainConfiguration {

}
