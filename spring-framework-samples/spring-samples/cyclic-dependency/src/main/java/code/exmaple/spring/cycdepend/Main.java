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
		SpringApplication application = new SpringApplication(Main.class);
		ConfigurableApplicationContext context = application.run(args);
		Model model = context.getBean(Model.class);
		Student student = context.getBean(Student.class);
		System.out.println(model);
		System.out.println(student);
		System.out.println(model.student);
		System.out.println(student.model);
	}
}
