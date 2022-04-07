package com.lontyu.sharding.shardingsphere.utils;

public class StringUtils {
	
	
	public static boolean containsAll(String target, String... subStrings) {
		if (target == null || target.length() == 0 || subStrings.length == 0) {
			return false;
		}
		for (String subString : subStrings) {
			if (!target.contains(subString)) {
				return false;
			}
		}
		return true;
	}

	public static boolean containsAny(String target, String... subStrings) {
		int count = 0;
		for (String subString : subStrings) {
			if (target.contains(subString))
				count++;
		}
		return count > 0;
	}
	
	public static void main(String[] args) {
		int i = "Hello Wolrd".indexOf(null);
		System.out.println(i);
	}
}
