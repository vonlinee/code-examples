package spring.boot.aop.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BeanE {

    // @Resource
    BeanC beanC;

    public void setBeanC(BeanC beanC) {
        System.out.println("setBeanC => " + beanC);
        this.beanC = beanC;
    }
}
