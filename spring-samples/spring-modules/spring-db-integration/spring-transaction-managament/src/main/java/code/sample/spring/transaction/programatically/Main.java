package code.sample.spring.transaction.programatically;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import code.sample.spring.transaction.dao.BookPurchaseDao;

public class Main {
	private static ApplicationContext context;

	public static void main(String[] args) throws Exception {
		context = new ClassPathXmlApplicationContext("spring-tx-declaratively.xml");
		BookPurchaseDao bookPurchaseDao = (BookPurchaseDao) context.getBean("bookPurchaseDaoImpl");
		bookPurchaseDao.bookPurchase(1, 1, "soni1234");
	}
}
