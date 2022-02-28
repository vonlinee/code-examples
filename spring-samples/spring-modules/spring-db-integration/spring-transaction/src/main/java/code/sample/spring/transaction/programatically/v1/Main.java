package code.sample.spring.transaction.programatically.v1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * using-transaction-manager-directly
 * transaction-management-with-jdbc
 * @author someone
 */
public class Main {
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("spring.xml");
		AccountDaoImpl accountDaoImpl = (AccountDaoImpl) context.getBean("accountDaoImpl");
//		accountDaoImpl.transferMoney(200);
	}
}