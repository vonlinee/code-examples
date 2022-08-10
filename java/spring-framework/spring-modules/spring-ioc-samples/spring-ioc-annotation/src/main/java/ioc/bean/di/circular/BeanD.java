package ioc.bean.di.circular;

import org.springframework.stereotype.Component;

//@Component
public class BeanD {

	private BeanC beanC;
	
	public BeanD(BeanC beanC) {
		this.beanC = beanC;
	}
}