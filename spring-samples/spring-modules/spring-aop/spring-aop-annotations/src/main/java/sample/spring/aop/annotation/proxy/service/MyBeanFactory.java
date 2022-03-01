package sample.spring.aop.annotation.proxy.service;

public class MyBeanFactory {
	
	/**
	 * 获取的Bean对象实际是代理对象
	 * @param beanName
	 * @return
	 * Object
	 */
	public Object getBean(String beanName) {
		if (beanName.equals("circle"))
			return new CircleProxy();
		if (beanName.equals("square"))
			return new SquareProxy();
		return null;
	}
}
