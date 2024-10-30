package org.example.springboot.config;

import org.springframework.context.annotation.Bean;

public class SimpleClass {

    @Bean
    public A a() {
        return new A();
    }
}
