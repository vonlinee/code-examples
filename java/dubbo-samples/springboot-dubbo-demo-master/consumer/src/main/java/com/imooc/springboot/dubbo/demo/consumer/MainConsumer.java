package com.imooc.springboot.dubbo.demo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MainConsumer {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainConsumer.class, args);

        A bean = context.getBean(A.class);

        System.out.println(bean);
    }
}