package sample.spring.advanced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import sample.spring.advanced.bean.Model;

@Configuration
@ComponentScan("sample.spring.advanced")
public class GlobalConfig {
	
	@Bean
	public Model model() {
		return new Model();
	}
}
