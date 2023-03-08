package design.pattern.decorator;

import java.io.*;

/**
 * 有时想用一些现存的组件。这些组件可能只是完成了一些核心功能。但在不改变其结构的情况下，可以动态地扩展其功能。 所有这些都可以釆用装饰器模式来实现
 * <p>
 * 装饰器模式（Decorator Pattern）允许向一个现有的对象添加新的功能，同时又不改变其结构。
 * 这种类型的设计模式属于结构型模式，它是作为现有的类的一个包装
 * <p>
 * <a href="https://www.cnblogs.com/yxlaisj/p/10446504.html">...</a>
 *
 * @author vonline
 */
public class DecoratorPattern {

	static void print(Coffee c) {
		System.out.println("花费了: " + c.getCost());
		System.out.println("配料: " + c.getIngredients());


	}

	public static void main(String[] args) {
		// 原味咖啡
		Coffee c = new SimpleCoffee();
		print(c);

		// 增加牛奶的咖啡
		c = new WithMilk(c);
		print(c);

		// 再加一点糖
		c = new WithSugar(c);
		
		print(c);
	}
}

//从上个例子可以看出，装饰器模式的结构很像代理模式，装饰器模式的请求转发过程很像责任链模式，只不过：
//职责链模式在转发请求过程中，最多只有一个对象会处理请求，而装饰器模式则有多个对象处一个请求。
//装饰器模式是代替增加子类的一种解决方案，体现了聚合/合成复用原则的思想，尽量使用组合的方式来扩展功能，
//这样就把基本功能和扩展功能解耦了，使得代码可复用，可维护，灵活。关键点在于装饰器模式可以动态地为对象增加扩展功能。