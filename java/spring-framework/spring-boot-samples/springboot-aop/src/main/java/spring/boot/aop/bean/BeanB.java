package spring.boot.aop.bean;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

//@Component
public class BeanB {

    @Resource
    public BeanA beanA;
}
