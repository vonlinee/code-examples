package code.sample.spring.jdbc.template.v1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import code.sample.spring.jdbc.dao.UserDao;

public class Main {
    private static ApplicationContext context;

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("spring.xml");
        UserDao userDao = (UserDao) context.getBean("userDaoImpl");
//        userDao.createTeacher();
//        userDao.insertStudent(new Student(6, "roshni"));
        userDao.displayData();
    }
}
