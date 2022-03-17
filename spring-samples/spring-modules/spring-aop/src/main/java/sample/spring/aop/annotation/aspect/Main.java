package sample.spring.aop.annotation.aspect;

import org.springframework.context.ApplicationContext;

import sample.spring.aop.SpringUtils;

public class Main {
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = SpringUtils.loadContext(Main.class, "spring-config.xml");
		Performer performer = (Performer) context.getBean("performer");
		performer.perform();
	}
}
