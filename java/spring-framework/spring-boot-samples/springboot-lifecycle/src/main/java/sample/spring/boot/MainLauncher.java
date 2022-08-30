package sample.spring.boot;

import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sample.spring.boot.controller.TestController;
import sample.spring.boot.service.ITestService;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@SpringBootConfiguration
@PropertySource(value = "classpath:jdbc.properties")
public class MainLauncher {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainLauncher.class);
        ConfigurableApplicationContext context = app.run(args);

        TestController controller = context.getBean(TestController.class);

        controller.test();
    }
}
