package code.pocket.base.utils;

import java.util.List;
import java.util.Map;

/**
 * Validator, throw {@code RuntimeException}
 * @author vonline
 *
 */
public final class Validator {
	
	/**
	 * 
	 * @param arr
	 * @param message
	 */
	public static void whenContains(String str, String regex, String message) {
		whenBlank(str, "string cannot be blank");
		if (str.contains(regex)) {
			throw new RuntimeException(message);
		}
	}
	
	/**
	 * 
	 * @param arr
	 * @param message
	 */
	public static void whenNotMatch(String str, String regex, String message) {
		whenBlank(str, "string cannot be blank");
	}
	
	/**
	 * 
	 * @param arr
	 * @param message
	 */
	public static void whenEmpty(Object[] arr, String message) {
		whenNull(arr, "array cannot be null");
		if (arr.length == 0) {
			throw new RuntimeException(message);
		}
	}
	
	/**
	 * 
	 * @param map
	 * @param message
	 */
	public static void whenEmpty(Map<String, Object> map, String message) {
		whenNull(map, "map cannot be null");
		if (map.isEmpty()) {
			throw new RuntimeException(message);
		}
	}
	
	public static void whenEmpty(List<?> list, String message) {
		whenNull(list, "list cannot be null");
		if (list.isEmpty()) {
			throw new RuntimeException(message);
		}
	}
	
	public static void whenNotContains(Map<String, Object> map, String key, String message) {
		whenNull(map, "map cannot be null");
		if (map.containsKey(key)) {
			throw new RuntimeException(message);
		}
	}
	
	public static void whenBlank(String str, String message) {
		if (str != null && str.length() == 0) {
			throw new RuntimeException(message);
		}
	}
	
	/**
	 * 
	 * @param obj
	 * @param message
	 */
	public static void whenNull(Object obj, String message) {
		if (obj == null) {
			throw new RuntimeException(message);
		}
	}
	
	public static void whenTrue(boolean expression, String message) {
		if (!expression) {
			throw new RuntimeException(message);
		}
	}
	
	public static void whenLengthNotInRange(String str, String message) {
		if (str.length() > 0) {
			throw new RuntimeException(message);
		}
	}
}
