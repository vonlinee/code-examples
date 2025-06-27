package org.example.webservice.impl;

import org.example.webservice.HelloService;

import java.util.Map;

public class HelloServiceImpl implements HelloService {

    @Override
    public Map<String, Object> test(Map<String, Object> param) {
        System.out.println(Thread.currentThread().getName());
        return param;
    }
}
