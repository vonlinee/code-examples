package sample.spring.aop.annotation.proxy;

import sample.spring.aop.annotation.proxy.service.MyBeanFactory;
import sample.spring.aop.annotation.proxy.service.Shape;

/**
 * 静态代理
 * @author someone
 */
public class Main {
	private static MyBeanFactory myBeanFactory = new MyBeanFactory();

	public static void main(String[] args) {
		Shape shape = null;
		shape = (Shape) myBeanFactory.getBean("circle");
		shape.draw();
		System.out.println("*****************************************************************************");
		shape = (Shape) myBeanFactory.getBean("square");
		shape.draw();
	}
}
