package io.maker.codegen;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ClassUtils;

@SpringBootApplication
public class CodeGenApplication {
	public static void main(String[] args) throws ClassNotFoundException, LinkageError {
		// SpringApplication.run(CodeGenApplication.class, args);
		
		
		Class<?> clazz = ClassUtils.forName("aaa.aaa.String", Thread.currentThread().getContextClassLoader());
		
		System.out.println(clazz);
	}
}
