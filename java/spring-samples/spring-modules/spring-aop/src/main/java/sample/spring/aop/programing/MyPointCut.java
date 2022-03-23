package sample.spring.aop.programing;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.stereotype.Component;

/**
 * org.aspectj.lang.reflect.Pointcut
 * org.aspectj.weaver.patterns.Pointcut
 */
@Component
public class MyPointCut implements Pointcut {

	@Override
	public ClassFilter getClassFilter() {
		return ClassFilter.TRUE;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return MethodMatcher.TRUE;
	}
}
