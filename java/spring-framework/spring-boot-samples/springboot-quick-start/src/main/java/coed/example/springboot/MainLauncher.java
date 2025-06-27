package coed.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MainLauncher extends SpringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainLauncher.class, args);
    }
}
