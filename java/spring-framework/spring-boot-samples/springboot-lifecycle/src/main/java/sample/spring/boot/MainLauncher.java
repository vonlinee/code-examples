package sample.spring.boot;

import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@SpringBootConfiguration
@PropertySource(value = "classpath:jdbc.properties")
public class MainLauncher {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainLauncher.class);
        app.run(args);

    }
}
