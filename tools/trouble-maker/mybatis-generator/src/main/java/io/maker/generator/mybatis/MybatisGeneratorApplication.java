package io.maker.generator.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class
})
@MapperScans(value = {
		@MapperScan(value = {"io.maker.generator.mybatis.mapper"})
})
@ComponentScan(basePackages = {
		"io.maker.generator.mybatis"
})
public class MybatisGeneratorApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MybatisGeneratorApplication.class);
	
	@Value("${server.port}")
	private int port;
	
	public static void main(String[] args) {
		SpringApplication.run(MybatisGeneratorApplication.class, args);
		
		LOGGER.info("localhost:8888/doc.html");
	}
}
