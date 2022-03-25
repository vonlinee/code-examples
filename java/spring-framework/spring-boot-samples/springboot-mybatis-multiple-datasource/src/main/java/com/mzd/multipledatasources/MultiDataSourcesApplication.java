package com.mzd.multipledatasources;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mzd.multipledatasources.mapper")
public class MultiDataSourcesApplication {
	public static void main(String[] args) {
		SpringApplication.run(MultiDataSourcesApplication.class, args);
	}
}
