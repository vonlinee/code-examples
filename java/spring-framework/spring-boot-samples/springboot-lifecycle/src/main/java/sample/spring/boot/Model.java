package sample.spring.boot;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
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
