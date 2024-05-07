package org.example.java8.primary.generic;

import org.example.java8.primary.generic.bean.Apple;
import org.example.java8.primary.generic.bean.Fruit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 逆变：指能够使用比原始指定的派生类型的派生程度更大(更具体的)的类型,即 ? extends Number，逆变的代价是不能写
 * 协变covariant：指能够使用比原始指定的派生类型的派生程度更小(不太具体的)的类型.即? super Double，协变的代价是不能
 * 
 * PECS（producer-extends, consumer-super）
 * 如果参数化类型表示一个生产者，就使用<? extends T>。比如list.get(0)这种，list作为数据源producer；
 * 如果它表示一个消费者，就使用<? super T>。比如：list.add(new Apple())，list作为数据处理端consumer。
 * 
 */
public class TestGenericMethod {

	public static void main(String[] args) {

		// 协变
		List<? extends Fruit> list1 = new ArrayList<Apple>();
		
		// list1.add(new Fruit());
		// list1.add(new Apple()); // 不能添加，因为Fruit有多个子类型，不知道具体是哪一个
		// list1.add(new Object());
		Fruit fruit = list1.get(0); // 获取只能拿到范围最高的类型
		
		// 逆变
		List<? super Apple> list2 = new ArrayList<Fruit>();
		// 对于赋值来说，元素只能是Apple及Apple的父类
		// list2.add(new Fruit());  // 存放的是Apple或Apple的父类，但是只能取到最低限度，再往上会有风险
		list2.add(new Apple()); // 只能添加Apple及Apple的子类，利用向上转型
		Object object = list2.get(0); // get会损失泛型信息，取可能的泛型的上限

	}

	public static void test5() {
		TestGenericMethod test = new TestGenericMethod();
		// List<Map<String, Object>> list = test.method();
		// list.add(null);
		Map<Object, Object> map = test.<Map<Object, Object>>method();
		System.out.println(map);

		Map<Object, Object> map1 = test.<HashMap<Object, Object>>method();
		System.out.println(map1);

		// java.util.HashMap cannot be cast to java.lang.String
		String map2 = test.<String>method();
		System.out.println(map2);
	}

	@SuppressWarnings("unchecked")
	public <T> T method() {
		return (T) new HashMap<>();
	}

	public static Object method1() {
		return new Object();
	}

	public <T> void genericMethod(T type) {

	}

	public static void test1() {
		ArrayList<Object> list1;
		ArrayList<String> list2 = new ArrayList<>();
		// Type mismatch: cannot convert from ArrayList<String> to ArrayList<Object>
//		list1 = list2;
		// Type mismatch: cannot convert from ArrayList<Object> to ArrayList<String>
//		list1.add(new Object());
	}

	public static double sum(List<? extends Number> list) {
		double result = 0;
		for (int i = 0; i < list.size(); i++) {
			result += list.get(i).doubleValue();
		}
		// The method add(int, capture#3-of ? extends Number) in the type
		// List<capture#3-of ? extends Number>
		// is not applicable for the arguments (int)
		// list.add(10f);
		return result;
	}

	public static void test2() {
		ArrayList<Double> list2 = new ArrayList<>();
		// The method removeIf(List<E>, Predicate<E>) in the type TestGenericType
		// is not applicable for the arguments (ArrayList<Double>, new
		// Filter<Number>(){})
//		removeIf(list2, new Filter<Number>() {
//			@Override
//			public boolean test(Number number) {
//				return false;
//			}
//		});
	}

	public static <T> void m1(T list) {
		
	}
	
	public static <E> List<E> removeIf(List<E> list, Predicate<E> test) {
		for (int i = 0; i < list.size(); i++) {
			if (test.test(list.get(i))) {
				list.remove(i);
			}
		}
		return list;
	}
}

interface Filter<T> {
	boolean test(T number);
}
