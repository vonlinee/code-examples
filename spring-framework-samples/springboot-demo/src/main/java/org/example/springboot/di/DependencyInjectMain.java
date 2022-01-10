package org.example.springboot.di;

import org.example.springboot.di.bean.Model;
import org.example.springboot.di.bean.Student;
import org.example.springboot.di.config.IocContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
//@Import(value = {
//		Student.class
//})
public class DependencyInjectMain {
	
	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DependencyInjectMain.class, args);
		Student student = context.getBean(Student.class);
		Model model = context.getBean(Model.class);
		System.out.println(student);
		System.out.println(model);
	}
}
