package org.setamv.shardingsphere.sample.dynamic.spel;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * 动态路由表达式的根对象
 * @author setamv
 * @date 2021-04-20
 */
@Data
public class DynamicRouteExpressionRootObject {

    private final Method method;

    private final Object[] args;

    private final Object target;

    private final Class<?> targetClass;


}
