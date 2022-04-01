package code.sample.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class // 防止不注入数据源无法启动
})
public class MainLauncher {
	public static void main(String[] args) {
		SpringApplication.run(MainLauncher.class, args);
	}
}
