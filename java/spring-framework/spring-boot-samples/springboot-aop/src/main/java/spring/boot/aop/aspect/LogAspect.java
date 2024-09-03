package spring.boot.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * <a href="https://blog.csdn.net/weixin_45583303/article/details/118565966">...</a>
 */
@Aspect
@Component
public class LogAspect {

    public LogAspect() {
        // throw new RuntimeException();
    }

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
