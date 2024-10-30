package org.example.springboot.aspect;

import org.example.springboot.service.UserService;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.stereotype.Component;

@Component
public class MyPointcut implements Pointcut {
    @Override
    public ClassFilter getClassFilter() {
        return clazz -> clazz == UserService.class;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return null;
    }
}
