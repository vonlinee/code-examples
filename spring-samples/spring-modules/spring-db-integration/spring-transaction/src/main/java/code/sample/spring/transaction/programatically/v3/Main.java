package code.sample.spring.transaction.programatically.v3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import code.sample.spring.transaction.programatically.v2.dao.BookPurchaseDao;

public class Main {
	private static ApplicationContext context;
	
	public static void main(String[] args) {
		context=new ClassPathXmlApplicationContext("spring.xml"); 
		BookPurchaseDao bookPurchaseDao = (BookPurchaseDao)context.getBean("bookPurchaseDaoImpl");
		bookPurchaseDao.bookPurchase(1, 1, "soni1234");
	}
}
