package org.springboot.sample.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

    public String getHelloMessage() {
        return "Hello Shanhy";
    }

}
