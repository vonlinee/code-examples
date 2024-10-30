package org.example.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class A {

    B b;

    @Autowired
    public void setB(B b) {
        this.b = b;
    }
}
