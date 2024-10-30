package org.example.springboot.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAspect {

    @Pointcut("execution(* org.example..*..UserService.*(..))")
    private void pointcut() {
    }

    @Around("pointcut()")
    public Object aroundUserServiceOperation(ProceedingJoinPoint pjp) throws Throwable {  //2
        long start = System.currentTimeMillis();
//        Object retVal = pjp.proceed();
        long duration = System.currentTimeMillis() - start;
        System.out.printf("time for pointcut method is %d seconds%n", duration);
        return null;
    }

    @After(value = "execution(* org.example..*..UserService.*(..))")
    public void afterUserServiceOperation(JoinPoint joinPoint) throws Throwable {
        System.out.println("before");
    }

    @Before(value = "execution(* org.example..*..UserService.*(..))")
    public void beforeUserServiceOperation(JoinPoint joinPoint) throws Throwable {
        System.out.println("before");
    }

    @AfterReturning(value = "execution(* org.example..*..UserService.*(..))", returning = "returnValue")
    public Object afterReturningUserServiceOperation(JoinPoint joinPoint, Object returnValue) throws Throwable {
        return returnValue;
    }

    @AfterThrowing(value = "execution(* org.example..*..UserService.*(..))", throwing = "throwable")
    public void afterThrowingUserServiceOperation(JoinPoint joinPoint, Throwable throwable) throws Throwable {

    }
}
