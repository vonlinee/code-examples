package sample.springboot.aop.aspect;

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

	@Pointcut("execution(public * com.aismall.testaop.controller.*.*(..))")
	public void pointcut() {
		System.out.println("pointcut");
	}
	
	@Before("execution(public * sample.springboot.aop.service.UserService.*(..))")
	public void logBeforeUserLoginIn() {
		System.out.println("-----------------");
	}
}
