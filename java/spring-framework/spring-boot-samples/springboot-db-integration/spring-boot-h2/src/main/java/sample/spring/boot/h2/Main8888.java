package sample.spring.boot.h2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 命名策略
 * https://blog.csdn.net/jackyxwr/article/details/8618908
 */
@SpringBootApplication
public class Main8888 {
	
	@Value("${server.port}")
	private int port;
	
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main8888.class, args);
        
        Main8888 launcherClass = context.getBean(Main8888.class);
        System.out.println("访问 => http://localhost:" + launcherClass.port + "/h2-console/");
    }
}
