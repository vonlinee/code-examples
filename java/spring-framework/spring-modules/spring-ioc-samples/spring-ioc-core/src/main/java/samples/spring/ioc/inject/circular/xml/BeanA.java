package samples.spring.ioc.inject.circular.xml;

public class BeanA {

	private BeanB beanB;

	public BeanA() {
	}

	public BeanA(BeanB beanB) {
		this.beanB = beanB;
	}

	public BeanB getBeanB() {
		return beanB;
	}

	public void setBeanB(BeanB beanB) {
		this.beanB = beanB;
	}
}