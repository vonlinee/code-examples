package sample.spring.core.xml.impl;

import org.springframework.context.ApplicationContext;
import sample.spring.core.SpringUtils;

public class TestBeanPostProcessor {

    public static void main(String[] args) {
        ApplicationContext context = SpringUtils.loadXmlAppContext();
        Object object = context.getBean("object");
        System.out.println(object);
    }
}
