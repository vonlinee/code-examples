package org.examples.spring.context.annotation;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class D {
    C c;

    public D(C c) {
        this.c = c;
    }
}
