package design.pattern.decorator;

/**
 * 咖啡的"装饰器"，可以给咖啡添加各种"配料"
 * 咖啡对象的装饰器类，同样实现Coffee接口，定义一个Coffe对象的引用，在构造器中进行初始化。并且将getCost（）和getIntegredients()方法转发给被装饰对象。
 */
public abstract class CoffeeDecorator implements Coffee {
	
	protected final Coffee decoratedCoffee;

	/**
	 * 在构造方法中，初始化咖啡对象的引用
	 */
	public CoffeeDecorator(Coffee coffee) {
		this.decoratedCoffee = coffee;
	}

	/**
	 * 装饰器父类中直接转发"请求"至引用对象
	 */
	public double getCost() {
		return decoratedCoffee.getCost();
	}

	public String getIngredients() {
		return decoratedCoffee.getIngredients();
	}
}