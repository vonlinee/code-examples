package org.example.spring.bean;

import org.springframework.stereotype.Component;

@Component
public class A {

    B b;

    public A() {
    }

    public A(B b) {
        this.b = b;
    }

    //    public A(B b) {
//        this.b = b;
//    }

//    @Autowired
//    public void setB(B b) {
//        this.b = b;
//    }
}
