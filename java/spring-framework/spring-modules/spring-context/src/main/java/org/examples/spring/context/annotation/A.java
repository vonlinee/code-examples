package org.examples.spring.context.annotation;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class A {
    @Resource
    B b;
}
