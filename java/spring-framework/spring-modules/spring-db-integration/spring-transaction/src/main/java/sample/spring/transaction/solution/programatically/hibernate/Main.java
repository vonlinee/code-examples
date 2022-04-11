package sample.spring.transaction.solution.programatically.hibernate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("spring.xml");
		AccountServiceImpl service = context.getBean("accountService", AccountServiceImpl.class);
		service.transferMoney();
	}
}
