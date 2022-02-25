package code.sample.spring.jdbc.datasource.v2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import code.sample.spring.jdbc.dao.UserDao;

//2. Using SingleConnectionDataSource
public class Main {
	private static ApplicationContext context;

	public static void main(String[] args) {
		context = new ClassPathXmlApplicationContext("spring.xml");
		UserDao userDao = (UserDao) context.getBean("userDaoImpl");
		userDao.displayData();
	}
}
