package samples.spring.ioc.inject.circular.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

//IOC 按照上面所示的 <bean> 配置，实例化 A 的时候发现 A 依赖于 B 于是去实例化 B
//（此时 A 创建未结束，处于创建中的状态），而发现 B 又依赖于 A ，于是就这样循环下去，最终导致 OOM
// 因此Spring会检测出循环依赖，在启动时就报错
public class Test {
	public static void main(String[] args) {
		String xml = Test.class.getResource("circular-denpendency.xml").toExternalForm();
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(xml);
		// Setter注入没有循环依赖的问题，而构造器注入Spring无法自己解决
		BeanA beanA = context.getBean(BeanA.class);
		BeanB beanB = context.getBean(BeanB.class);
		
		System.out.println(beanA);
		System.out.println(beanB);
	}
}
