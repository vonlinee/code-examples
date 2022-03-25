package ioc.bean.di.circular.xml;

public class BeanB {

	private BeanA beanA;

	public BeanB() {
	}

	public BeanA getBeanA() {
		return beanA;
	}

	public void setBeanA(BeanA beanA) {
		this.beanA = beanA;
	}

	public BeanB(BeanA beanA) {
		this.beanA = beanA;
	}
}