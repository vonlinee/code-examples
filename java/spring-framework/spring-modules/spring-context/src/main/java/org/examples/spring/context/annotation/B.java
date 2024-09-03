package org.examples.spring.context.annotation;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class B {

    @Resource
    A a;
}
