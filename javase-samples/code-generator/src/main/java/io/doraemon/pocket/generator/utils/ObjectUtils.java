package io.doraemon.pocket.generator.utils;

public class ObjectUtils {

	@SuppressWarnings("unchecked")
	public static <T> T[] cast(Object[] array) {
		try {
			return (T[]) array;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param array
	 * @return
	 */
	public static String[] cast2Strings(Object[] array) {
		try {
			if (array[0] instanceof String) {
				return (String[]) array;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object obj) {
		try {
			return (T) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
