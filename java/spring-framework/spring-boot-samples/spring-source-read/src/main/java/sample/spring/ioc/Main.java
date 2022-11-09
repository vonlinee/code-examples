package sample.spring.ioc;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import sample.spring.bean.Model;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
}, proxyBeanMethods = false)
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();

        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context.getBeanFactory();

        if (args.length > 0) {
            context.refresh();
            Model main = beanFactory.getBean(Model.class);
            System.out.println(main);
        }



        Bean.C c = beanFactory.getBean(Bean.C.class);

        System.out.println(c);
    }
}
