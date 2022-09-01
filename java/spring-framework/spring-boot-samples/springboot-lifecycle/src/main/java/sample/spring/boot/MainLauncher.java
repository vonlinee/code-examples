package sample.spring.boot;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.AbstractApplicationContext;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
@PropertySource(value = "classpath:jdbc.properties")
public class MainLauncher {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MainLauncher.class);

        ConfigurableApplicationContext context = app.run(args);

        AbstractApplicationContext acc = (AbstractApplicationContext) context;
        // ApplicationContext准备好后，Bean实际上还未被创建
        // acc.refresh();
        System.out.println("=============");
//        ConfigurableListableBeanFactory beanFactory = acc.getBeanFactory();
//        Utils.printObject(beanFactory);
//
//        DefaultListableBeanFactory beanFactory1 = (DefaultListableBeanFactory) beanFactory;
//
//        Object controller = beanFactory1.getBean("testController");
//        Utils.printObject(controller);

        int beanDefinitionCount = context.getBeanDefinitionCount();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();

        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            if (!beanDefinition.isLazyInit()) {
                System.out.println(beanDefinitionName);
            }
        }
    }
}
