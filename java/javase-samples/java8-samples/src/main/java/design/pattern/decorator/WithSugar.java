package design.pattern.decorator;

/**
 * 另一个具体装饰器类，用来给咖啡加糖，一样的逻辑。
 * 
 * @author vonline
 *
 */
public class WithSugar extends CoffeeDecorator {

	public WithSugar(Coffee coffee) {
		super(coffee);
	}

	@Override
	public double getCost() {
		return super.getCost() + 1;
	}

	@Override
	public String getIngredients() {
		return super.getIngredients() + ", Sugar";
	}
}