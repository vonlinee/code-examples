package design.pattern.decorator;

/**
 * 咖啡
 */
public interface Coffee {
	/** 获取价格 */
	double getCost();

	/** 获取配料 */
	String getIngredients();
}