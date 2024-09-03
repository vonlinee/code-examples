package com.imooc.springboot.dubbo.demo.consumer;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class A {

    @Resource
    A a;
}
