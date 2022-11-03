package sample.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://blog.csdn.net/qq_41520636/article/details/119858462
 * @author Von
 *
 */
@RestController
@SpringBootApplication
public class Main {
	
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}