package sample.spring.boot.entity;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Component
public class Model implements InitializingBean {

    @Autowired
    Model model;

    @Resource
    OptionalBean bean;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this);
    }

    @PostConstruct
    public void checkDependency() {
        Assert.notNull(bean, "==================");
    }
}
