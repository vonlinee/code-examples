package org.examples.spring.context.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "org.examples.spring.context.annotation")
public class BeanContainer {

}
