package sample.spring.transaction.annotation;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sample.spring.transaction.annotation.config.DbConfig;
import sample.spring.transaction.annotation.service.IService;

public class Test {

    @Autowired
    @Qualifier("transactional")
    IService transactional;

    @Autowired
    @Qualifier("template")
    IService template;

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DbConfig.class);

        IService service1 = context.getBean("transactional", IService.class);
    }
}
