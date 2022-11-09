package sample.spring.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    static final Logger LOG = LoggerFactory.getLogger(LogAspect.class);

    @Around(value = "execution(public * sample..*.AccountServiceImpl.*(..))")
    public Object intercept(ProceedingJoinPoint joinPoint) throws Throwable {
        LOG.info("日志开始");
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        Object result = joinPoint.proceed();
        LOG.info("日志结束");
        return result;
    }
}
