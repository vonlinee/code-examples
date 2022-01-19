package code.pocket.base.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.istack.internal.FinalArrayList;

public class StringUtils {
	
	/**
	 * 
	 * @param strings
	 * @return
	 * List<String>
	 */
	public static List<String> uniformLength(List<String> strings) {
		int maxLen = strings.get(0).length();
		int size = strings.size();
		for (int i = 0; i < size; i++) {
			for (int j = 1; j < size; j++) {
				int nextLen = strings.get(j).length();
				if (nextLen > maxLen) {
					maxLen = nextLen;
				}
			}
		}
		for (int i = 0; i < size; i++) {
			strings.add(i, appendBlank(strings.remove(i), maxLen));
		}
		return strings;
	}
	
	/**
	 * 
	 * @param sequence
	 * @param c
	 * @param len
	 * @return
	 * String
	 */
	public static String append(String sequence, char c, int len) {
		int i = len - sequence.length();
		if (i > 0) {
			for (int j = 0; j < i; j++) {
				sequence += c;
			}
		}
		return sequence;
	}
	
	/**
	 * 
	 * @param sequence
	 * @param len
	 * @return
	 * String
	 */
	public static String appendBlank(String sequence, int len) {
		int i = len - sequence.length();
		if (i > 0) {
			for (int j = 0; j < i; j++) {
				sequence += " ";
			}
		}
		return sequence;
	}
}
