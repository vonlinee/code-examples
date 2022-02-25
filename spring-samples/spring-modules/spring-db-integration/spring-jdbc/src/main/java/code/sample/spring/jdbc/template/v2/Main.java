package code.sample.spring.jdbc.template.v2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("spring.xml");
//        UserDao userDao = (UserDao) context.getBean("userDaoImpl");
//		userDao.insertStudent(new Student(7, "avni"));
//        userDao.displayData();
    }
}
