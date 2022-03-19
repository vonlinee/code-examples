package sample.spring.aop.programing;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.stereotype.Component;

/**
 * Advisor, Pointcut, Advice三者的关系
 */
@Component
public class BeforeAdvisor implements PointcutAdvisor {

	@Override
	public Pointcut getPointcut() {
		System.out.println(this);
		int i = 1 / 0;
		return null;
	}

	@Override
	public Advice getAdvice() {
		return null;
	}

	@Override
	public boolean isPerInstance() {
		return false;
	}
}
