package ioc.bean.di.circular;

public class BeanD {

	private BeanC beanC;
	
	public BeanD(BeanC beanC) {
		this.beanC = beanC;
	}
}