package spring.beans.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.beans.xml.factory.Student;

public class Main {

    private static ApplicationContext context;

    public static void main(String[] args) throws Exception {
        context = new ClassPathXmlApplicationContext("xml/spring.xml");
        Student student1 = context.getBean(Student.class);
        Student student2 = context.getBean(Student.class);
        System.out.println(student1);
        System.out.println(student2);
        System.out.println(student1 == student2);

    }
}
