package sample.spring.boot.entity;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class Model implements InitializingBean {

    @Autowired
    Model model;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this);
    }
}
