package samples.spring.ioc.inject.circular.annotation;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class A {

    @Resource
    A a;
}
