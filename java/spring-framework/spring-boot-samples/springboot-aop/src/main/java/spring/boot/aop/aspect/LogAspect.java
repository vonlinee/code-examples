package spring.boot.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * https://blog.csdn.net/weixin_45583303/article/details/118565966
 */
@Aspect
@Component
public class LogAspect {

    // org.aspectj.lang.annotation.Pointcut
    // @Pointcut("execution(public * com.aismall.testaop.controller.*.*(..))")
    public void pointcut() {
        System.out.println("pointcut");
    }

    // 建议切面方法返回Object
    @Around("execution(public * spring.boot.aop.service.UserService.*(..))")
    public Object logBeforeUserLoginIn(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("执行前");
        Object returnValue = joinPoint.proceed();
        System.out.println("执行后");
        return returnValue;
    }
}
