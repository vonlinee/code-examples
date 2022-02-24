package code.sample.springcloude;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class OrderRibbonMain80 {
    public static void main(String[] args) {
        SpringApplication.run(OrderRibbonMain80.class, args);
    }
}
