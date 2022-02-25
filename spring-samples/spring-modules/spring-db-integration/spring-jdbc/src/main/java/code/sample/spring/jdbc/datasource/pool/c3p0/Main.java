package code.sample.spring.jdbc.datasource.pool.c3p0;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import code.sample.spring.jdbc.datasource.v1.UserDao;

public class Main {
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("spring.xml");
		UserDao userDao = (UserDao) context.getBean("userDaoImpl");
		userDao.displayData();
	}
}