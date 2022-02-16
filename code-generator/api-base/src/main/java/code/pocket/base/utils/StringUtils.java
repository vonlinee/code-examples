package code.pocket.base.utils;

import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {

	public static boolean isBlank(String str) {
		return false;
	}

	/**
	 * 
	 * @param strings
	 * @return List<String>
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
	 * @return String
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
	 * @return String
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

	public static String[] split(String str, String delimeter) {
		StringTokenizer st = new StringTokenizer(str, delimeter);
		int i = st.countTokens();
		String[] strings = new String[i];
		while (st.hasMoreTokens()) {
			strings[i - (++i)] = st.nextToken();
		}
		return strings;
	}
}
