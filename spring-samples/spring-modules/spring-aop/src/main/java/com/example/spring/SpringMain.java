package com.example.spring;

import com.example.spring.aware.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringMain {

    private static final ApplicationContext context =
            new ClassPathXmlApplicationContext("bean.xml");

    public static void main(String[] args) {

        Bean bean = context.getBean("bean1", Bean.class);
    }
}
