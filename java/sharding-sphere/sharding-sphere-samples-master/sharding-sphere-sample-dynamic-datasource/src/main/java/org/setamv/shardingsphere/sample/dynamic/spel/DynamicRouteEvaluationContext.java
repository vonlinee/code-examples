package org.setamv.shardingsphere.sample.dynamic.spel;

import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

/**
 * 动态路由表达式的根对象
 * @author setamv
 * @date 2021-04-20
 */
public class DynamicRouteEvaluationContext extends MethodBasedEvaluationContext {

    DynamicRouteEvaluationContext(Object rootObject, Method method, Object[] arguments,
                           ParameterNameDiscoverer parameterNameDiscoverer) {
        super(rootObject, method, arguments, parameterNameDiscoverer);
    }
}
