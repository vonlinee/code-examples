package samples.spring.ioc.inject.circular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringThreeLevelCacheDemo {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainTest.class, args);


    }
}
