package code.example.spring.transaction;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import code.example.spring.transaction.dao.AccountDaoImpl;

public class Main {
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("spring.xml");
		AccountDaoImpl accountDaoImpl = (AccountDaoImpl) context.getBean("accountDaoImpl");
		accountDaoImpl.transferMoney(200);
	}
}
