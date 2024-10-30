package org.example.springboot.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Import(MyDeferredSelector.class)
@Component
@EnableAspectJAutoProxy
public class Config {

//    @Bean
    public FactoryBean<A> factoryBeanA() {
        return new FactoryBean<A>() {
            @Override
            public A getObject() throws Exception {
                return new A();
            }

            @Override
            public Class<?> getObjectType() {
                return A.class;
            }
        };
    }
}
