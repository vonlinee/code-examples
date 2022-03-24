package sample.spring.ioc.circular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Test {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Test.class, args);
		
	}
}
