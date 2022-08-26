package io.devpl.sdk.api;

import io.devpl.sdk.support.spring.DevplApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication()
@ImportResource("classpath:applicationContext.xml")
public class DevplUI {
    public static void main(String[] args) {
        DevplApplication app = new DevplApplication(DevplUI.class);
        app.setAppName("Devpl-UI");
        ConfigurableApplicationContext context = app.run(args);
    }
}
