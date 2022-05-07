package sample.redis.spring.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication {
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication application = new SpringApplication(MainApplication.class);
		ConfigurableApplicationContext context = application.run(args);
		String[] aliases = context.getAliases("spring");
		System.out.println(aliases);
		
	}
}
