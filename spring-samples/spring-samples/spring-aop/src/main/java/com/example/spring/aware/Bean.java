package com.example.spring.aware;

import org.springframework.beans.factory.BeanNameAware;

public class Bean implements BeanNameAware {
    @Override
    public void setBeanName(String name) {
        System.out.println(name);
    }
}
