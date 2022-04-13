package samples.spring.ioc.inject.circular;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import samples.spring.ioc.lifecycle.Model;
import samples.spring.ioc.lifecycle.Student;

@SpringBootApplication
public class Main {
	
	static {
		System.setProperty("spring.devtools.restart.enabled", "false");
	}
	
	public static ConfigurableApplicationContext context ;
	
	public static void main(String[] args) {
		context = SpringApplication.run(Main.class, args);
	}
	
	public static void showCycleDependency() {
		//spring-context
		//AbstractApplicationContext.getBean(Class<?> requiredType)
		Model model = context.getBean(Model.class);
		//BeanFactory
		//ConfigurableListableBeanFactory
		Student student = context.getBean(Student.class);
		System.out.println(model); 			//bean.Model@2392212b
		System.out.println(student); 		//bean.Student@5b43e173
		System.out.println(model.student); 	//bean.Student@5b43e173
		System.out.println(student.model); 	//bean.Model@2392212b
		System.out.println(model == student.model); 	//true
		System.out.println(student == model.student); 	//true
	}
}