package com.ruoyi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动程序
 * @author ruoyi
 */
@SpringBootApplication
public class RuoYiApplication<T> {

    private static final Logger LOG = LoggerFactory.getLogger(RuoYiApplication.class);

    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext context = SpringApplication.run(RuoYiApplication.class, args);
        LOG.info("Swagger地址: http://localhost:8888/swagger-ui/index.html#/");


        List<ArrayList<?>> list1 = new ArrayList<>();
        List<List<?>> list2 = new ArrayList<>();

        method(list1, list2, AbstractList.class);
    }


    public static <K extends V, V> void method(List<? extends K> list, List<? super V> list1, Class< V> clazz) {
        list1.get(0);
    }

}

class Person {

}

class Student extends Person {

}

class Faculty extends Person {

}
