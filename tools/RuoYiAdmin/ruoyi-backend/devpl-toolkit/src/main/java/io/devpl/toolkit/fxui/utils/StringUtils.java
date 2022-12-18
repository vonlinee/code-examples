package io.devpl.toolkit.fxui.utils;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 统一工具类
 */
public final class StringUtils {

    private StringUtils() {
    }

    public static final String EMPTY_BLANK = "";

    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean hasText(final CharSequence cs) {
        return org.springframework.util.StringUtils.hasText(cs);
    }

    public static boolean hasText(final CharSequence... sequence) {
        if (sequence == null) return false;
        for (CharSequence charSequence : sequence) {
            if (!hasText(charSequence)) return false;
        }
        return true;
    }

    public static boolean isNotEmpty(final CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isNotEmpty(cs);
    }

    /**
     * convert string from slash style to camel style, such as my_course will convert to MyCourse
     * @param str
     * @return
     */
    public static String dbStringToCamelStyle(String str) {
        if (str != null) {
            if (str.contains("_")) {
                str = str.toLowerCase();
                StringBuilder sb = new StringBuilder();
                sb.append(String.valueOf(str.charAt(0)).toUpperCase());
                for (int i = 1; i < str.length(); i++) {
                    char c = str.charAt(i);
                    if (c != '_') {
                        sb.append(c);
                    } else {
                        if (i + 1 < str.length()) {
                            sb.append(String.valueOf(str.charAt(i + 1)).toUpperCase());
                            i++;
                        }
                    }
                }
                return sb.toString();
            } else {
                String firstChar = String.valueOf(str.charAt(0)).toUpperCase();
                String otherChars = str.substring(1);
                return firstChar + otherChars;
            }
        }
        return null;
    }

    public static String[] split(String source, String separator) {
        return org.apache.commons.lang3.StringUtils.split(source, separator);
    }

    public static boolean containsAny(final CharSequence cs, final CharSequence... searchCharSequences) {
        return org.apache.commons.lang3.StringUtils.containsAny(cs, searchCharSequences);
    }

    /**
     * <p>Checks if any of the CharSequences are empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isAnyEmpty((String) null)    = true
     * StringUtils.isAnyEmpty((String[]) null)  = false
     * StringUtils.isAnyEmpty(null, "foo")      = true
     * StringUtils.isAnyEmpty("", "bar")        = true
     * StringUtils.isAnyEmpty("bob", "")        = true
     * StringUtils.isAnyEmpty("  bob  ", null)  = true
     * StringUtils.isAnyEmpty(" ", "bar")       = false
     * StringUtils.isAnyEmpty("foo", "bar")     = false
     * StringUtils.isAnyEmpty(new String[]{})   = false
     * StringUtils.isAnyEmpty(new String[]{""}) = true
     * </pre>
     *
     * @param css  the CharSequences to check, may be null or empty
     * @return {@code true} if any of the CharSequences are empty or null
     * @since 3.2
     */
    public static boolean isAnyEmpty(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return false;
        }
        for (final CharSequence cs : css) {
            if (isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }
}
