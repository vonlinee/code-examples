package samples.spring.ioc.inject.circular.xml;

public class BeanD {

	private BeanC beanC;
	
	public BeanD(BeanC beanC) {
		this.beanC = beanC;
	}
}