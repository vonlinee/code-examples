package io.devpl.spring;

import io.devpl.spring.boot.DevplSpringApplication;
import io.devpl.spring.web.utils.ParamWrapper;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.MultiValueMap;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = DevplSpringApplication.run(MainApplication.class, args);

        ParamWrapper wrapper;
    }
}
