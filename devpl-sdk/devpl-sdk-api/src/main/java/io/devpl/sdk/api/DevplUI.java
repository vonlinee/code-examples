package io.devpl.sdk.api;

import io.devpl.sdk.support.spring.DevplApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication()
@ImportResource("classpath:applicationContext.xml")
public class DevplUI {
    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(DevplUI.class, args);

        String[] beanDefinitionNames = context.getBeanDefinitionNames();

        for (String beanDefinitionName : beanDefinitionNames) {

            Object bean = context.getBean(beanDefinitionName);
            String name = bean.getClass().getName();
            if (name.startsWith("io")) {
                System.out.println(name);
            }
        }
    }
}
