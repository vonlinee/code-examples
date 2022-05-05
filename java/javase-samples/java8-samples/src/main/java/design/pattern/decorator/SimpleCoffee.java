package design.pattern.decorator;

/**
 * 原味咖啡
 */
public class SimpleCoffee implements Coffee {

	@Override
	public double getCost() {
		return 1;
	}

	@Override
	public String getIngredients() {
		return "Coffee";
	}
}