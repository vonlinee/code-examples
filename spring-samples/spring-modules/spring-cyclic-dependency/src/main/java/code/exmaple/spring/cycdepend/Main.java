package code.exmaple.spring.cycdepend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import code.exmaple.spring.cycdepend.bean.Model;
import code.exmaple.spring.cycdepend.bean.Student;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		System.setProperty("spring.devtools.restart.enabled", "false");
		
		//spring-context
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		
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
