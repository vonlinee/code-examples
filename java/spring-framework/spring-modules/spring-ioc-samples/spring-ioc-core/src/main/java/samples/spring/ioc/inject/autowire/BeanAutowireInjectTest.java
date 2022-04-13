package samples.spring.ioc.inject.autowire;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {
		"samples.spring.ioc.bean", 
		"samples.spring.ioc.inject.autowire"
})
public class BeanAutowireInjectTest {
	
	// @Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
//	Inject
	public static void main(String[] args) {
		SpringApplication.run(BeanAutowireInjectTest.class, args);
	}
}
