package ioc.programing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import ioc.programing.bean.Model;
import ioc.utils.SpringUtils;

/**
 * https://spring.io/blog/2016/03/04/core-container-refinements-in-spring-framework-4-3
 */
@SpringBootApplication
public class Main {

	static {
		System.setProperty("spring.devtools.restart.enabled", "false");
	}
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		
		Model model = context.getBean(Model.class);
		
		System.out.println(model);
		
	}
}
