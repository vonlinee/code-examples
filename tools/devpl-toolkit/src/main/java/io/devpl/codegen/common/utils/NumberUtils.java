package io.devpl.codegen.common.utils;

public class NumberUtils {

    /**
     * <p>Convert a <code>String</code> to a <code>Integer</code>, handling
     * hex (0xhhhh) and octal (0dddd) notations.
     * N.B. a leading zero means octal; spaces are not trimmed.</p>
     *
     * <p>Returns <code>null</code> if the string is <code>null</code>.</p>
     * @param str a <code>String</code> to convert, may be null
     * @return converted <code>Integer</code> (or null if the input is null)
     * @throws NumberFormatException if the value cannot be converted
     */
    public static int decodeInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        // decode() handles 0xAABD and 0777 (hex and octal) as well.
        try {
            return Integer.decode(str); // won’t return null
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 返回默认值
     * @param s
     * @param defaultValue
     * @return
     */
    public static int parseInt(String s, int defaultValue) {
        if (s == null || s.isEmpty()) return defaultValue;
        try {
            return Integer.parseInt(s, 10);
        } catch (NumberFormatException exception) {
            return defaultValue;
        }
    }
}
