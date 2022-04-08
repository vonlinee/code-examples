package org.setamv.shardingsphere.sample.dynamic.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.setamv.shardingsphere.sample.dynamic.config.DynamicRouteDataSourceContext;
import org.springframework.stereotype.Component;

/**
 * Controller切面
 *
 * @author setamv
 * @date 2021-04-17
 */
@Component
@Aspect
public class ControllerAspect {

    /**
     * 方法切面
     */
    @Pointcut("execution(* org.setamv.shardingsphere.sample.dynamic.controller.*.*(..))")
    public void controllerMethod() {
        // 纯声明，没有方法体
    }

    /**
     * 切面动作
     * @param joinPoint
     */
    @After("controllerMethod()")
    public void afterControllerMethod(JoinPoint joinPoint) {
        // 清除动态数据源的路由上下文信息
        DynamicRouteDataSourceContext.clearDataSource();
    }
}
