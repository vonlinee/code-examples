package sample.spring.aop.annotation.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.InitializingBean;

@Aspect
public class Audience implements InitializingBean {
	
	@Pointcut("execution(* sample.spring.aop.annotation.aspect.Performer.*(..))")
	public void performance(){}
	
	@Before("performance()")
	public void takeSeats() {			// before advice
		System.out.println("BEFORE ADVICE : The audience is taking their seats and turning off their cellphones");
	}
	
	@Before("performance()")
	public void turnOffCellPhones() {		// before advice
		System.out.println("BEFORE ADVICE : The audience is turning off their cellphones");
	}
	
	@After("performance()")
	public void applaud() {		// after advice
		System.out.println("AFTER ADVICE : CLAP CLAP CLAP CLAP CLAP");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.performance();
	}
}