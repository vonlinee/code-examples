
package io.maker.base.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.CharSequenceUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

public final class StringUtils {

	private static final int NOT_FOUND = -1;

	public static final String DOUBLE_QUTATION = "\"";
	public static final String SINGLE_QUTATION = "'";
	public static final String NULL_STRING_HCASE = "NULL";
	public static final String NULL_STRING_LCASE = "null";

	/**
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 将字符串转换为同意最长的长度
	 * 
	 * @param strings 数组
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

	public static String[] split1(String str, String delimeter) {
		StringTokenizer st = new StringTokenizer(str, delimeter);
		int i = st.countTokens();
		String[] strings = new String[i];
		while (st.hasMoreTokens()) {
			strings[i - (++i)] = st.nextToken();
		}
		return strings;
	}

	private static final Pattern ALL_EN_WORDS = Pattern.compile("[a-zA-Z]+");
	private static final Pattern CONTAIN_EN_WORDS = Pattern.compile(".*[a-zA-z].*");

	public static boolean isAllEnWords(String str) {
		return ALL_EN_WORDS.matcher(str).matches();
	}

	public static boolean containEnWords(String str) {
		return CONTAIN_EN_WORDS.matcher(str).matches();
	}

	public static boolean isUpperCase(String str) {
		for (char c : str.toCharArray()) {
			if (!Character.isUpperCase(c))
				return false;
		}
		return true;
	}

	/**
	 * 字符串是否包含中文
	 * 
	 * @param str 待校验字符串
	 * @return true 包含中文字符 false 不包含中文字符
	 */
	public static boolean containChineseWords(String str) {
		if (str != null && str.length() != 0) {
			Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
			Matcher m = p.matcher(str);
			return m.find();
		}
		return false;
	}

	public static String upperFirst(String str) {
		if (str == null || str.length() == 0) {
			return "";
		}
		return Character.toUpperCase(str.toCharArray()[0]) + str.substring(1, str.length());
	}

	public static String wrapQuotation(String str, boolean doubleQutaion) {
		if (doubleQutaion) {
			if (!str.contains("\"")) {
				return "\"" + str + "\"";
			} else {
				if (str.startsWith("\"") && !str.endsWith("\""))
					return str + "\"";
				if (!str.startsWith("\"") && str.endsWith("\""))
					return "\"" + str;
				String substring = str.substring(1, str.length() - 1);
				if (substring.contains("\"")) {
					return "\"" + substring.replace("\"", "") + "\"";
				}
				return str;
			}
		} else {
			String c = SINGLE_QUTATION;
			if (!str.contains("\"")) {
				return c + str + c;
			} else {
				if (str.startsWith(c) && !str.endsWith(c))
					return str + c;
				if (!str.startsWith(c) && str.endsWith(c))
					return str + c;
				String substring = str.substring(1, str.length() - 1);
				if (substring.contains(c)) {
					return c + substring.replace(c, "") + c;
				}
				return str;
			}
		}
	}

	private static final int STRING_BUILDER_SIZE = 256;

	/**
	 * A String for a space character.
	 * 
	 * @since 3.2
	 */
	public static final String SPACE = " ";

	/**
	 * The empty String {@code ""}.
	 * @since 2.0
	 */
	public static final String EMPTY = "";

	/**
	 * A String for linefeed LF ("\n").
	 * @see <a href= "http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6"> JLF: Escape Sequences for Character and String Literals</a>
	 * @since 3.2
	 */
	public static final String LF = "\n";

	/**
	 * A String for carriage return CR ("\r").
	 * 
	 * @see <a href=
	 *      "http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF:
	 *      Escape Sequences for Character and String Literals</a>
	 * @since 3.2
	 */
	public static final String CR = "\r";

	/**
	 * Represents a failed index search.
	 * 
	 * @since 2.1
	 */
	public static final int INDEX_NOT_FOUND = -1;

	/**
	 * <p>
	 * The maximum size to which the padding constant(s) can expand.
	 * </p>
	 */
	private static final int PAD_LIMIT = 8192;

	/**
	 * Pattern used in {@link #stripAccents(String)}.
	 */
	private static final Pattern STRIP_ACCENTS_PATTERN = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

	/**
	 * TODO
	 * @param text
	 * @return
	 */
	public static boolean hasLength(String text) {
		return false;
	}

	public static boolean hasText(String text) {
		// TODO Auto-generated method stub
		return false;
	}
}