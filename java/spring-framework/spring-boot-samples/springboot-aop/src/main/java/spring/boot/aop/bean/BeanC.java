package spring.boot.aop.bean;

import org.springframework.stereotype.Component;

//@Component
public class BeanC {

    private final BeanD beanD;

    public BeanC(BeanD beanD) {
        this.beanD = beanD;
    }
}