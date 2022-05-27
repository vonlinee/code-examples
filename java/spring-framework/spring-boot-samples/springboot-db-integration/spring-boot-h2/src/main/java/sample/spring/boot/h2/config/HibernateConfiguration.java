package sample.spring.boot.h2.config;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {

//	@Bean
	public PhysicalNamingStrategy hibernateNamingStrategy() {
		return new DynamicNamingStrategy(null);
	}
	
	@Bean
	public ImplicitNamingStrategy implicitNamingStrategy() {
		return new DefaultImplicitNamingStrategy();
	}
}
