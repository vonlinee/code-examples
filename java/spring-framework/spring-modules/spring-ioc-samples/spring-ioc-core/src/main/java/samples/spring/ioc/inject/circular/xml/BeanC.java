package samples.spring.ioc.inject.circular.xml;

public class BeanC {

	private BeanD beanD;

	public BeanC(BeanD beanD) {
		this.beanD = beanD;
	}
}