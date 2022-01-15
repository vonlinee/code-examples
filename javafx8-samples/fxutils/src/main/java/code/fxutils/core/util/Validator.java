package code.fxutils.core.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * copy from {@code org.apache.commons.lang3.Validate} used for parameter
 * validation all methods throws {@code IllegalArgumentException}
 */
public abstract class Validator {

	private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter
			.ofPattern("yyyy-MM-dd hh:mm:ss");

	// 默认提示信息
	private static final String DEFAULT_NOT_NAN_EX_MESSAGE = "The validated value is not a number";
	private static final String DEFAULT_FINITE_EX_MESSAGE = "The value is invalid: %f";
	private static final String DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified exclusive range of %s to %s";
	private static final String DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE = "The value %s is not in the specified inclusive range of %s to %s";
	private static final String DEFAULT_MATCHES_PATTERN_EX = "The string %s does not match the pattern %s";
	private static final String DEFAULT_IS_NULL_EX_MESSAGE = "The validated object is null";
	private static final String DEFAULT_IS_TRUE_EX_MESSAGE = "The validated expression is false";
	private static final String DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE = "The validated array contains null element at index: %d";
	private static final String DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE = "The validated collection contains null element at index: %d";
	private static final String DEFAULT_NOT_BLANK_EX_MESSAGE = "The validated character sequence is blank";
	private static final String DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE = "The validated array is empty";
	private static final String DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence is empty";
	private static final String DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE = "The validated collection is empty";
	private static final String DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE = "The validated map is empty";
	private static final String DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE = "The validated array index is invalid: %d";
	private static final String DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE = "The validated character sequence index is invalid: %d";
	private static final String DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE = "The validated collection index is invalid: %d";
	private static final String DEFAULT_VALID_STATE_EX_MESSAGE = "The validated state is false";
	private static final String DEFAULT_IS_ASSIGNABLE_EX_MESSAGE = "Cannot assign a %s to a %s";
	private static final String DEFAULT_IS_INSTANCE_OF_EX_MESSAGE = "Expected type: %s, actual: %s";

	// Expression
	public static void whenTrue(final boolean expression, final String message) {
		if (expression) {
			throw new IllegalArgumentException(message);
		}
	}
	
	public static void isTrue(final boolean expression, final String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	public static <T extends Iterable<?>> T noNullElements(final T iterable) {
		return noNullElements(iterable, DEFAULT_NO_NULL_ELEMENTS_COLLECTION_EX_MESSAGE);
	}

	public static void isTrue(final boolean expression, final String message, final long value) {
		if (!expression) {
			throw new IllegalArgumentException(String.format(message, Long.valueOf(value)));
		}
	}

	public static void isTrue(final boolean expression, final String message, final double value) {
		if (!expression) {
			throw new IllegalArgumentException(String.format(message, Double.valueOf(value)));
		}
	}

	public static void isTrue(final boolean expression, final String message, final Object... values) {
		if (!expression) {
			throw new IllegalArgumentException(String.format(message, values));
		}
	}

	public static void isTrue(final boolean expression) {
		if (!expression) {
			throw new IllegalArgumentException(DEFAULT_IS_TRUE_EX_MESSAGE);
		}
	}

	// Null Check
	public static <T> T notNull(final T object) {
		return notNull(object, DEFAULT_IS_NULL_EX_MESSAGE);
	}

	public static <T> T notNull(final T object, final String message, final Object... values) {
		return Objects.requireNonNull(object, () -> String.format(message, values));
	}

	public static <T> T[] notEmpty(final T[] array, final String message, final Object... values) {
		Objects.requireNonNull(array, () -> String.format(message, values));
		if (array.length == 0) {
			throw new IllegalArgumentException(String.format(message, values));
		}
		return array;
	}

	public static <T> T[] notEmpty(final T[] array) {
		return notEmpty(array, DEFAULT_NOT_EMPTY_ARRAY_EX_MESSAGE);
	}

	public static <T extends Collection<?>> T notEmpty(final T collection, final String message,
			final Object... values) {
		Objects.requireNonNull(collection, () -> String.format(message, values));
		if (collection.isEmpty()) {
			throw new IllegalArgumentException(String.format(message, values));
		}
		return collection;
	}

	public static <T extends Collection<?>> T notEmpty(final T collection) {
		return notEmpty(collection, DEFAULT_NOT_EMPTY_COLLECTION_EX_MESSAGE);
	}

	public static <T> T[] noNullElements(final T[] array) {
		return noNullElements(array, DEFAULT_NO_NULL_ELEMENTS_ARRAY_EX_MESSAGE);
	}

	public static <T extends Iterable<?>> T noNullElements(final T iterable, final String message,
			final Object... values) {
		notNull(iterable);
		int i = 0;
		for (final Iterator<?> it = iterable.iterator(); it.hasNext(); i++) {
			if (it.next() == null) {
				final Object[] values2 = null;
				System.out.println(i);
				throw new IllegalArgumentException(String.format(message, values2));
			}
		}
		return iterable;
	}

	/**
	 * 数组没有NULL元素
	 * 
	 * @param array
	 * @param message
	 * @param values
	 * @return T[]
	 */
	public static <T> T[] noNullElements(final T[] array, final String message, final Object... values) {
		notNull(array);
		for (int i = 0; i < array.length; i++) {
			if (array[i] == null) {
				final Object[] values2 = null;
				throw new IllegalArgumentException(String.format(message, values2));
			}
		}
		return array;
	}

	public static <T extends Map<?, ?>> T notEmpty(final T map, final String message, final Object... values) {
		Objects.requireNonNull(map, () -> String.format(message, values));
		if (map.isEmpty()) {
			throw new IllegalArgumentException(String.format(message, values));
		}
		return map;
	}

	/**
	 * 
	 * @param map
	 * @param key
	 * @param message
	 *            void
	 */
	public static <T extends Map<K, ?>, K> void notExistKey(final T map, K key, final String message) {
		notNull(map);
		notEmpty(map);
		if (!map.containsKey(key)) {
			throw new IllegalArgumentException();
		}
	}

	public static <T extends Map<K, ?>, K> void notExistKey(final T map, K key) {
		notNull(map);
		notEmpty(map);
		if (!map.containsKey(key)) {
			throw new IllegalArgumentException(String.format("the key:{} does not exist in map", key));
		}
	}

	/**
	 * map has null value for the specificied key
	 * 
	 * @param <K>
	 * @param map
	 * @param key
	 * @param message
	 *            void
	 */
	public static <T extends Map<K, ?>, K> void notNullValue(final T map, K key, final String message) {
		notNull(map);
		notEmpty(map);
		notExistKey(map, key);
		if (map.get(key) == null) {
			throw new IllegalArgumentException(String.format("the value of key:{} in the map cannot be null", key));
		}
	}

	public static <T extends Map<K, ? extends CharSequence>, K> void notBlankValue(final T map, K key,
			final String message) {
		notNull(map);
		notEmpty(map);
		notExistKey(map, key);
		notNullValue(map, key, message);
		if (isBlank(map.get(key))) {
			throw new IllegalArgumentException(String.format("the value of key:{} in the map cannot be blank", key));
		}
	}



	/**
	 * 获取字符序列的长度，null check
	 * 
	 * @param cs
	 * @return int
	 */
	public static int length(final CharSequence cs) {
		return cs == null ? 0 : cs.length();
	}

	public static void notBlank(final CharSequence sequence) {
		if (isBlank(sequence)) {
			throw new IllegalArgumentException(DEFAULT_NOT_BLANK_EX_MESSAGE);
		}
	}

	public static <T extends Map<?, ?>> T notEmpty(final T map) {
		return notEmpty(map, DEFAULT_NOT_EMPTY_MAP_EX_MESSAGE);
	}

	public static <T extends CharSequence> T notEmpty(final T chars, final String message, final Object... values) {
		Objects.requireNonNull(chars, () -> String.format(message, values));
		if (chars.length() == 0) {
			throw new IllegalArgumentException(String.format(message, values));
		}
		return chars;
	}

	public static <T extends CharSequence> T notEmpty(final T chars) {
		return notEmpty(chars, DEFAULT_NOT_EMPTY_CHAR_SEQUENCE_EX_MESSAGE);
	}

	public static <T> T[] validIndex(final T[] array, final int index, final String message, final Object... values) {
		notNull(array);
		if (index < 0 || index >= array.length) {
			throw new IndexOutOfBoundsException(String.format(message, values));
		}
		return array;
	}

	public static <T> T[] validIndex(final T[] array, final int index) {
		return validIndex(array, index, DEFAULT_VALID_INDEX_ARRAY_EX_MESSAGE, Integer.valueOf(index));
	}

	public static <T extends Collection<?>> T validIndex(final T collection, final int index, final String message,
			final Object... values) {
		notNull(collection);
		if (index < 0 || index >= collection.size()) {
			throw new IndexOutOfBoundsException(String.format(message, values));
		}
		return collection;
	}

	public static <T extends Collection<?>> T validIndex(final T collection, final int index) {
		return validIndex(collection, index, DEFAULT_VALID_INDEX_COLLECTION_EX_MESSAGE, Integer.valueOf(index));
	}

	public static <T extends CharSequence> T validIndex(final T chars, final int index, final String message,
			final Object... values) {
		notNull(chars);
		if (index < 0 || index >= chars.length()) {
			throw new IndexOutOfBoundsException(String.format(message, values));
		}
		return chars;
	}

	public static <T extends CharSequence> T validIndex(final T chars, final int index) {
		return validIndex(chars, index, DEFAULT_VALID_INDEX_CHAR_SEQUENCE_EX_MESSAGE, Integer.valueOf(index));
	}

	public static void validState(final boolean expression) {
		if (!expression) {
			throw new IllegalStateException(DEFAULT_VALID_STATE_EX_MESSAGE);
		}
	}

	public static void validState(final boolean expression, final String message, final Object... values) {
		if (!expression) {
			throw new IllegalStateException(String.format(message, values));
		}
	}

	// matchesPattern
	// ---------------------------------------------------------------------------------

	public static void matchesPattern(final CharSequence input, final String pattern) {
		// TODO when breaking BC, consider returning input
		if (!Pattern.matches(pattern, input)) {
			throw new IllegalArgumentException(String.format(DEFAULT_MATCHES_PATTERN_EX, input, pattern));
		}
	}

	public static void matchesPattern(final CharSequence input, final String pattern, final String message,
			final Object... values) {
		// TODO when breaking BC, consider returning input
		if (!Pattern.matches(pattern, input)) {
			throw new IllegalArgumentException(String.format(message, values));
		}
	}

	public static void notNaN(final double value) {
		notNaN(value, DEFAULT_NOT_NAN_EX_MESSAGE);
	}

	public static void notNaN(final double value, final String message, final Object... values) {
		if (Double.isNaN(value)) {
			throw new IllegalArgumentException(String.format(message, values));
		}
	}

	// finite
	// ---------------------------------------------------------------------------------

	public static void finite(final double value) {
		finite(value, DEFAULT_FINITE_EX_MESSAGE, value);
	}

	public static void finite(final double value, final String message, final Object... values) {
		if (Double.isNaN(value) || Double.isInfinite(value)) {
			throw new IllegalArgumentException(String.format(message, values));
		}
	}

	public static <T> void inclusiveBetween(final T start, final T end, final Comparable<T> value) {
		// TODO when breaking BC, consider returning value
		if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
			throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
		}
	}

	public static <T> void inclusiveBetween(final T start, final T end, final Comparable<T> value, final String message,
			final Object... values) {
		// TODO when breaking BC, consider returning value
		if (value.compareTo(start) < 0 || value.compareTo(end) > 0) {
			throw new IllegalArgumentException(String.format(message, values));
		}
	}

	@SuppressWarnings("boxing")
	public static void inclusiveBetween(final long start, final long end, final long value) {
		// TODO when breaking BC, consider returning value
		if (value < start || value > end) {
			throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
		}
	}

	public static void inclusiveBetween(final long start, final long end, final long value, final String message) {
		// TODO when breaking BC, consider returning value
		if (value < start || value > end) {
			throw new IllegalArgumentException(message);
		}
	}

	@SuppressWarnings("boxing")
	public static void inclusiveBetween(final double start, final double end, final double value) {
		// TODO when breaking BC, consider returning value
		if (value < start || value > end) {
			throw new IllegalArgumentException(String.format(DEFAULT_INCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
		}
	}

	public static void inclusiveBetween(final double start, final double end, final double value,
			final String message) {
		// TODO when breaking BC, consider returning value
		if (value < start || value > end) {
			throw new IllegalArgumentException(message);
		}
	}

	public static <T> void exclusiveBetween(final T start, final T end, final Comparable<T> value) {
		// TODO when breaking BC, consider returning value
		if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
			throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
		}
	}

	public static <T> void exclusiveBetween(final T start, final T end, final Comparable<T> value, final String message,
			final Object... values) {
		// TODO when breaking BC, consider returning value
		if (value.compareTo(start) <= 0 || value.compareTo(end) >= 0) {
			throw new IllegalArgumentException(String.format(message, values));
		}
	}

	@SuppressWarnings("boxing")
	public static void exclusiveBetween(final long start, final long end, final long value) {
		// TODO when breaking BC, consider returning value
		if (value <= start || value >= end) {
			throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
		}
	}

	public static void exclusiveBetween(final long start, final long end, final long value, final String message) {
		// TODO when breaking BC, consider returning value
		if (value <= start || value >= end) {
			throw new IllegalArgumentException(message);
		}
	}

	@SuppressWarnings("boxing")
	public static void exclusiveBetween(final double start, final double end, final double value) {
		// TODO when breaking BC, consider returning value
		if (value <= start || value >= end) {
			throw new IllegalArgumentException(String.format(DEFAULT_EXCLUSIVE_BETWEEN_EX_MESSAGE, value, start, end));
		}
	}

	public static void exclusiveBetween(final double start, final double end, final double value,
			final String message) {
		// TODO when breaking BC, consider returning value
		if (value <= start || value >= end) {
			throw new IllegalArgumentException(message);
		}
	}

	public static void isInstanceOf(final Class<?> type, final Object obj) {
		// TODO when breaking BC, consider returning obj
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(String.format(DEFAULT_IS_INSTANCE_OF_EX_MESSAGE, type.getName(),
					obj == null ? "null" : obj.getClass().getName()));
		}
	}

	public static void isInstanceOf(final Class<?> type, final Object obj, final String message,
			final Object... values) {
		// TODO when breaking BC, consider returning obj
		if (!type.isInstance(obj)) {
			throw new IllegalArgumentException(String.format(message, values));
		}
	}

	// isAssignableFrom
	public static void isAssignableFrom(final Class<?> superType, final Class<?> type) {
		// TODO when breaking BC, consider returning type
		if (!superType.isAssignableFrom(type)) {
			throw new IllegalArgumentException(String.format(DEFAULT_IS_ASSIGNABLE_EX_MESSAGE,
					type == null ? "null" : type.getName(), superType.getName()));
		}
	}

	public static void isAssignableFrom(final Class<?> superType, final Class<?> type, final String message,
			final Object... values) {
		// TODO when breaking BC, consider returning type
		if (!superType.isAssignableFrom(type)) {
			throw new IllegalArgumentException(String.format(message, values));
		}
	}

	public static <T> void test(T value, Predicate<T> rule, String message) {
		if (rule.test(value)) {
			throw new IllegalArgumentException(message);
		}
	}

	public static <T> void testNegate(T value, Predicate<T> rule, String message) {
		if (rule.negate().test(value)) {
			throw new IllegalArgumentException(message);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> void testMultiAnd(T value, String message, Predicate<T>... rules) {
		if (rules.length == 1) {
			test(value, rules[0], message);
		}
		if (rules.length > 1) {
			Predicate<T> rule = rules[0];
			for (int i = 1; i < rules.length; i++) {
				rule.and(rules[i]);
			}
			test(value, rule, message);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> void testMultiOr(T value, String message, Predicate<T>... rules) {
		if (rules.length == 1) {
			test(value, rules[0], message);
		}
		if (rules.length > 1) {
			Predicate<T> rule = rules[0];
			for (int i = 1; i < rules.length; i++) {
				rule.or(rules[i]);
			}
			test(value, rule, message);
		}
	}

	// DateTime Validation
	public static LocalDateTime notValidDatetime(final String dt, String format) {
		try {
			return LocalDateTime.parse(dt, DateTimeFormatter.ofPattern(format));
		} catch (Exception e) {
			throw new IllegalArgumentException(String.format("日期:{}不符合日期格式:{}", dt, format));
		}
	}
	
	public static boolean dateTimeInRange(String s1, String e1, String s2, String e2, String format) {
		boolean result = false;
		try {
			LocalDateTime dts1 = LocalDateTime.parse(s1, DEFAULT_DATETIME_FORMATTER);
			LocalDateTime dte1 = LocalDateTime.parse(e1, DEFAULT_DATETIME_FORMATTER);
			LocalDateTime dts2 = LocalDateTime.parse(s1, DEFAULT_DATETIME_FORMATTER);
			LocalDateTime dte2 = LocalDateTime.parse(e2, DEFAULT_DATETIME_FORMATTER);
			// 开始时间大于结束时间
			if (dts1.compareTo(dte2) > 0 || dte1.compareTo(dts2) < 0) {
				result = true;
			}
			return result;
		} catch (Exception ignore) {
			// ignore
		}
		return result;
	}
	
	// 判断：返回true、false
	public static <T> boolean isNull(T value) {
		return value == null;
	}
	
	public static <T> boolean isEmpty(Map<?, ?> map) {
		notNull(map);
		return map.isEmpty();
	}
	
	public static <T> boolean isEmpty(List<?> list) {
		notNull(list);
		return list.isEmpty();
	}
	
	@SuppressWarnings("unchecked")
	public static <T> boolean isEmpty(T ... array) {
		notNull(array);
		return array.length == 0;
	}
	
	public static <T> boolean isEmpty(final CharSequence sequence) {
		notNull(sequence);
		return sequence.length() == 0;
	}
	
	public static <T> boolean isNullOrEmpty(final CharSequence sequence) {
		return sequence == null || sequence.length() == 0;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> boolean isNullOrEmpty(T ... array) {
		return array == null || array.length == 0;
	}
	
	/**
	 * 判断字符序列是否为空
	 * @param cs CharSequence
	 * @return boolean
	 */
	public static boolean isBlank(final CharSequence cs) {
		final int strLen = length(cs);
		if (strLen == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
