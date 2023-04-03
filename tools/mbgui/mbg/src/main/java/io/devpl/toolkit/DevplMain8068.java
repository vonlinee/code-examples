package io.devpl.toolkit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class DevplMain8068 {
    public static void main(String[] args) throws SQLException {
        try {
            SpringApplication.run(DevplMain8068.class, args);
            System.out.println("http://localhost:8068/");
        } catch (Throwable exception) {
            exception.printStackTrace();
        }
    }
}
