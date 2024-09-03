package sample.spring.aop.jdkproxy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Around("execution(public * *.jdkproxy.IUserService.*(..))")
    public Object logBeforeUserLoginIn(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("执行前");
        Object returnValue = joinPoint.proceed();
        System.out.println("执行后");
        return returnValue;
    }
}