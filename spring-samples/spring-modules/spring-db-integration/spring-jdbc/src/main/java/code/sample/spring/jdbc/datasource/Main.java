package code.sample.spring.jdbc.datasource;

import code.sample.spring.jdbc.dao.UserDao;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    private static ApplicationContext context;

    //1. Using DriverManagerDataSource
    public static void test1() {
        context = new ClassPathXmlApplicationContext("spring-config.xml");
        UserDao userDao = (UserDao) context.getBean("userDaoImpl");
        userDao.displayData();
    }


    /**
     * 2. Using SingleConnectionDataSource
     *
     * @param args
     */
    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("spring-config.xml");
        UserDao userDao = (UserDao) context.getBean("userDaoImpl");
        userDao.displayData();
    }
}

