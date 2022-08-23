package spring.boot.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude= {
		DataSourceAutoConfiguration.class
})
@EnableAspectJAutoProxy(proxyTargetClass=true, exposeProxy=true)
public class MainLauncher {
	public static void main(String[] args) {
		SpringApplication.run(MainLauncher.class, args);
	}
}
