package samples.spring.ioc.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public final class CodeAnalysis {

	public static List<StackTraceElement> getStackTraceOfCurrentThread(Predicate<String> ignoreRule) {
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
		List<StackTraceElement> list = new ArrayList<>();
		for (StackTraceElement element : stackTraces) {
			if (ignoreRule.test(element.getClassName())) {
				continue;
			}
			list.add(element);
		}
		return list;
	}
	
	public static void printStackTrace() {
		List<StackTraceElement> list = getStackTraceOfCurrentThread();
		printlnList(list);
	}
	
	public static List<StackTraceElement> getStackTraceOfCurrentThread() {
		StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
		List<StackTraceElement> list = new ArrayList<>();
		for (StackTraceElement element : stackTraces) {
			list.add(element);
		}
		return list;
	}

	public static <E> void printlnList(List<E> list) {
		for (E e : list) {
			System.out.println(e);
		}
	}
	
	public static <K, V> void printlnMap(Map<K, V> map) {
		System.out.println("[");
		for (Map.Entry<K, V> entry : map.entrySet()) {
			System.out.println("\t" + entry.getKey() + " : " + entry.getValue());
		}
		System.out.println("]");
	}
}
