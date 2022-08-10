package io.maker.codegen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CodeGenApplication {
	public static void main(String[] args) throws ClassNotFoundException, LinkageError {
		SpringApplication.run(CodeGenApplication.class, args);
	}
}
