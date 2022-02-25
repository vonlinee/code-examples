package code.exmaple.spring.cycdepend.circular;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "code.exmaple.spring.cycdepend.circular" })
public class TestConfig {
	
}