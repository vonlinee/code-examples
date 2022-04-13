package samples.spring.ioc.inject.advanced;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude= {
		DataSourceAutoConfiguration.class
})
@ComponentScan(basePackages= {
		"samples.spring.ioc.bean", 
		"samples.spring.ioc.inject.advanced"
})
public class AppTest {
	
	// @Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
//	Inject
	public static void main(String[] args) {
		SpringApplication.run(AppTest.class, args);
	}
}
