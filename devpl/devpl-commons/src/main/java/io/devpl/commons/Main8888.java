package io.devpl.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import io.devpl.commons.context.AppContextInitializer;

@SpringBootApplication
public class Main8888 {
	public static void main(String[] args) {
		
		SpringApplication app = new SpringApplication(Main8888.class);
		app.addInitializers(new AppContextInitializer());
		
		ConfigurableApplicationContext context = app.run(args);
		
	}
}
