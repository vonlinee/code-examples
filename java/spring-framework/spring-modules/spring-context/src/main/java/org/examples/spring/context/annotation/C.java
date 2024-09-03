package org.examples.spring.context.annotation;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class C {
    D d;

    public C(D d) {
        this.d = d;
    }
}
