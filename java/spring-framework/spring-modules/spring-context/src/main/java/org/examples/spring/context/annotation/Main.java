package org.examples.spring.context.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanContainer.class);

        A a = context.getBean(A.class);

        System.out.println(a);

        C c = context.getBean(C.class);
    }
}
