package io.maker.codegen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class CodeGenApplication {
	public static void main(String[] args) {
		SpringApplication.run(CodeGenApplication.class, args);
	}
}
