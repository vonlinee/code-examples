package org.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.example.springboot.config.A;

/**
 * @see org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator
 * @see AbstractApplicationContext#refresh()
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        SpringApplication application = new SpringApplication();

        application.setAllowCircularReferences(false);

        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder();

        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);

        context.getBean(A.class);
    }
}
