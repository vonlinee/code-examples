package sample.java.primary.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import io.netty.handler.codec.AsciiHeadersEncoder.NewlineType;

public class TestGenericType {

	public static void main(String[] args) {

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
		// The method add(int, capture#3-of ? extends Number) in the type List<capture#3-of ? extends Number> 
		// is not applicable for the arguments (int)
		// list.add(10f);
		return result;
	}
	
	public static void test2() {
		ArrayList<Double> list2 = new ArrayList<>();
		// The method removeIf(List<E>, Predicate<E>) in the type TestGenericType 
		// is not applicable for the arguments (ArrayList<Double>, new Filter<Number>(){})
//		removeIf(list2, new Filter<Number>() {
//			@Override
//			public boolean test(Number number) {
//				return false;
//			}
//		});
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

interface Filter<Number> {
	boolean test(Number number);
}

