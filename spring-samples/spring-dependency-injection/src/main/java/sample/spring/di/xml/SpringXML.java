package sample.spring.di.xml;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Spring使用XML进行配置
 * @author someone
 */
public class SpringXML {
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = 
				new ClassPathXmlApplicationContext("/spring/spring-bean.xml");
		context.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
			@Override
			public void onApplicationEvent(ApplicationEvent event) {
				System.out.println("=====================");
			}
		});
		
		Student student = context.getBean(Student.class);
		System.out.println(student);
	}
}
