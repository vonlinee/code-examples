package coed.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@SpringBootApplication
public class MainLauncher {
    public static void main(String[] args) {
        SpringApplication.run(MainLauncher.class, args);
    }

    @Scheduled(cron ="*/2 * * * * ?")
    public void test() {

    }
}
