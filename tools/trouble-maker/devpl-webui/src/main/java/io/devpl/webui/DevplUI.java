package io.devpl.webui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DevplUI {
    public static void main(String[] args) {
        SpringApplication.run(DevplUI.class, args);
    }
}
