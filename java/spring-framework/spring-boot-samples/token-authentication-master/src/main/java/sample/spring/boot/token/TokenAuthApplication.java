package sample.spring.boot.token;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"sample.spring.boot.token.mapper"}) // @MapperScan要加在启动类上，不能加载@Configuration配置类上
public class TokenAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(TokenAuthApplication.class, args);
	}
}

