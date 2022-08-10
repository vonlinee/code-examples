package ioc.bean.di.circular;

import org.springframework.stereotype.Component;

//@Component
public class BeanC {

	private BeanD beanD;

	public BeanC(BeanD beanD) {
		this.beanD = beanD;
	}
}