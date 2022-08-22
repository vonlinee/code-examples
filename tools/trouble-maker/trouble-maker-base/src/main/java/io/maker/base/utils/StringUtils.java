package io.maker.base.utils;


import com.google.common.base.Preconditions;

import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     * JDK的随机UUID
     *
     * @return
     */
    public static String simpleUUID() {
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

    /**
     * 分割字符串
     *
     * @param str
     * @param delimeter
     * @return
     */
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

    public static boolean containsAlmostOne(String parent, CharSequence... sequences) {
        for (CharSequence s : sequences) {
            if (parent.contains(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean in(String target, String... group) {
        if (group.length == 0) return false;
        for (String s : group) {
            if (target.equals(s)) return true;
        }
        return false;
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
     *
     * @since 2.0
     */
    public static final String EMPTY = "";

    /**
     * A String for linefeed LF ("\n").
     *
     * @see <a href=
     * "http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">
     * JLF: Escape Sequences for Character and String Literals</a>
     * @since 3.2
     */
    public static final String LF = "\n";

    /**
     * A String for carriage return CR ("\r").
     *
     * @see <a href=
     * "http://docs.oracle.com/javase/specs/jls/se7/html/jls-3.html#jls-3.10.6">JLF:
     * Escape Sequences for Character and String Literals</a>
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
     *
     * @param text
     * @return
     */
    public static boolean hasLength(String text) {
        return false;
    }

    public static boolean hasText(String text) {
        return false;
    }

    /**
     * <p>
     * Joins the elements of the provided {@code Iterable} into a single String
     * containing the provided elements.
     * </p>
     *
     * <p>
     * No delimiter is added before or after the list. A {@code null} separator is
     * the same as an empty String ("").
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[], String)}.
     * </p>
     *
     * @param iterable  the {@code Iterable} providing the values to join together,
     *                  may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null iterator input
     * @since 2.3
     */
    public static String join(final Iterable<?> iterable, final String separator) {
        if (iterable == null) {
            return null;
        }
        return join(iterable.iterator(), separator);
    }

    /**
     * 使用指定分隔符拼接字符串
     *
     * @param separator
     * @param items
     * @return
     */
    public static String join(final String separator, String... items) {
        return join(Arrays.asList(items).iterator(), separator);
    }

    public static final String DEFAULT_SEPARATOR = ",";

    /**
     * 使用指定分隔符拼接字符串
     *
     * @param separator
     * @param items
     * @return
     */
    public static String join(String... items) {
        return join(Arrays.asList(items).iterator(), DEFAULT_SEPARATOR);
    }

    /**
     * <p>
     * Joins the elements of the provided {@code Iterator} into a single String
     * containing the provided elements.
     * </p>
     *
     * <p>
     * No delimiter is added before or after the list. A {@code null} separator is
     * the same as an empty String ("").
     * </p>
     *
     * <p>
     * See the examples here: {@link #join(Object[], String)}.
     * </p>
     *
     * @param iterator  the {@code Iterator} of values to join together, may be null
     * @param separator the separator character to use, null treated as ""
     * @return the joined String, {@code null} if null iterator input
     */
    public static String join(final Iterator<?> iterator, final String separator) {
        // handle null, zero and one elements before building a buffer
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return EMPTY;
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            return Objects.toString(first, "");
        }
        // two or more elements
        final StringBuilder buf = new StringBuilder(STRING_BUILDER_SIZE); // Java default is 16, probably too small
        if (first != null) {
            buf.append(first);
        }
        while (iterator.hasNext()) {
            if (separator != null) {
                buf.append(separator);
            }
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * 指定编码的字符串长度
     */
    public static int length(String str, String charset) {
        int len = 0;
        int j = 0;
        byte[] bytes = str.getBytes(Charset.forName(charset));
        while (bytes.length > 0) {
            short tmpst = (short) (bytes[j] & 0xF0);
            if (tmpst >= 0xB0) {
                if (tmpst < 0xC0 || ((tmpst == 0xC0) || (tmpst == 0xD0))) {
                    j += 2;
                    len += 2;
                } else if (tmpst == 0xE0) {
                    j += 3;
                    len += 2;
                } else if (tmpst == 0xF0) {
                    short tmpst0 = (short) (((short) bytes[j]) & 0x0F);
                    if (tmpst0 == 0) {
                        j += 4;
                        len += 2;
                    } else if ((tmpst0 > 0) && (tmpst0 < 12)) {
                        j += 5;
                        len += 2;
                    } else if (tmpst0 > 11) {
                        j += 6;
                        len += 2;
                    }
                }
            } else {
                j += 1;
                len += 1;
            }
            if (j > bytes.length - 1) {
                break;
            }
        }
        return len;
    }

    /**
     * <p>
     * Checks if a String is whitespace, empty ("") or null.
     * </p>
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否包含空格
     *
     * @param sequence
     * @return
     */
    public static boolean containWhiteSpace(CharSequence sequence) {
        return sequence == null || sequence.toString().contains(" ");
    }

    /**
     * 重复字符串指定次数，然后new一个新字符串
     *
     * @param string
     * @param count
     * @return
     */
    public static String repeat(String string, int count) {
        Validator.whenNull(string);
        if (count <= 1) {
            Validator.whenTrue(count >= 0, "invalid count: %s", count);
            return (count == 0) ? "" : string;
        }
        final int len = string.length();
        final long longSize = (long) len * (long) count;
        final int size = (int) longSize;
        if (size != longSize) {
            throw new ArrayIndexOutOfBoundsException("Required array size too large: " + longSize);
        }
        final char[] array = new char[size];
        string.getChars(0, len, array, 0);
        int n;
        for (n = len; n < size - n; n <<= 1) {
            System.arraycopy(array, 0, array, n, n);
        }
        System.arraycopy(array, 0, array, n, size - n);
        return new String(array);
    }

    /**
     * Returns the given {@code template} string with each occurrence of {@code "%s"} replaced with
     * the corresponding argument value from {@code args}; or, if the placeholder and argument counts
     * do not match, returns a best-effort form of that string. Will not throw an exception under
     * normal conditions.
     *
     * <p><b>Note:</b> For most string-formatting needs, use {@link String#format String.format},
     * {@link java.io.PrintWriter#format PrintWriter.format}, and related methods. These support the
     * full range of <a
     * href="https://docs.oracle.com/javase/9/docs/api/java/util/Formatter.html#syntax">format
     * specifiers</a>, and alert you to usage errors by throwing {@link
     * java.util.IllegalFormatException}.
     *
     * <p>In certain cases, such as outputting debugging information or constructing a message to be
     * used for another unchecked exception, an exception during string formatting would serve little
     * purpose except to supplant the real information you were trying to provide. These are the cases
     * this method is made for; it instead generates a best-effort string with all supplied argument
     * values present. This method is also useful in environments such as GWT where {@code
     * String.format} is not available. As an example, method implementations of the {@link
     * Preconditions} class use this formatter, for both of the reasons just discussed.
     *
     * <p><b>Warning:</b> Only the exact two-character placeholder sequence {@code "%s"} is
     * recognized.
     *
     * @param template a string containing zero or more {@code "%s"} placeholder sequences. {@code
     *                 null} is treated as the four-character string {@code "null"}.
     * @param args     the arguments to be substituted into the message template. The first argument
     *                 specified is substituted for the first occurrence of {@code "%s"} in the template, and so
     *                 forth. A {@code null} argument is converted to the four-character string {@code "null"};
     *                 non-null values are converted to strings using {@link Object#toString()}.
     * @since 25.1
     */
    public static String format(String template, Object... args) {
        template = String.valueOf(template); // null -> "null"
        if (args == null) {
            args = new Object[]{"(Object[])null"};
        } else {
            for (int i = 0; i < args.length; i++) {
                args[i] = valueOf(args[i]);
            }
        }
        // start substituting the arguments into the '%s' placeholders
        StringBuilder builder = new StringBuilder(template.length() + 16 * args.length);
        int templateStart = 0;
        int i = 0;
        while (i < args.length) {
            int placeholderStart = template.indexOf("%s", templateStart);
            if (placeholderStart == -1) {
                break;
            }
            builder.append(template, templateStart, placeholderStart);
            builder.append(args[i++]);
            templateStart = placeholderStart + 2;
        }
        builder.append(template, templateStart, template.length());

        // if we run out of placeholders, append the extra args in square braces
        if (i < args.length) {
            builder.append(" [");
            builder.append(args[i++]);
            while (i < args.length) {
                builder.append(", ");
                builder.append(args[i++]);
            }
            builder.append(']');
        }

        return builder.toString();
    }

    /**
     * 会对输入参数的可能情况进行检测
     *
     * @param o
     * @return
     */
    public static String valueOf(Object o) {
        if (o == null) {
            return NULL_STRING_HCASE;
        }
        try {
            return o.toString();
        } catch (Exception e) {
            // Default toString() behavior - see Object.toString()
            String objectToString = o.getClass().getName() + '@' + Integer.toHexString(System.identityHashCode(o));
            // Log is created inline with fixed name to avoid forcing Proguard to create another class.
            return "<" + objectToString + " threw " + e.getClass().getName() + ">";
        }
    }
}