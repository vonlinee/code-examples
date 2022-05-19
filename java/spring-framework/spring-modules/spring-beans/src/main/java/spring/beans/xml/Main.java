package spring.beans.xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import spring.beans.xml.factory.Student;
import spring.beans.xml.interfaces.Filter;

public class Main {

    private static ApplicationContext context;

    static {
        System.setProperty("jps.track.ap.dependencies", "false");
        context = loadSpringXml();
    }

    private static ApplicationContext loadSpringXml() {
        context = new ClassPathXmlApplicationContext("xml/spring.xml");

        System.out.println("======================");
        return context;
    }

    public static void main(String[] args) throws Exception {
        test2();
    }

    public static void test1() {

        Student student1 = context.getBean(Student.class);
        Student student2 = context.getBean(Student.class);
        System.out.println(student1);
        System.out.println(student2);
        System.out.println(student1 == student2);

    }

    public static void test2() {
        Filter filter = context.getBean(Filter.class);

        System.out.println(filter);
    }
}
