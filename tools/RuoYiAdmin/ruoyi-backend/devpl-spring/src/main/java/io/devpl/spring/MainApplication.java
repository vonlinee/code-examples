package io.devpl.spring;

import io.devpl.spring.boot.DevplSpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = DevplSpringApplication.run(MainApplication.class, args);
    }
}
