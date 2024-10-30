package org.example.spring.bean;

import org.springframework.stereotype.Component;

@Component
public class B {

    A a;

    public B() {
    }

    public B(A a) {
        this.a = a;
    }

    //    @Autowired
//    public void setA(A a) {
//        this.a = a;
//    }

}
