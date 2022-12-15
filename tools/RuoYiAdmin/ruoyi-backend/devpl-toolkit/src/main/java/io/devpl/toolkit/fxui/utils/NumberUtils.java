package io.devpl.toolkit.fxui.utils;

public class NumberUtils {

    /**
     * <p>Convert a {@code String} to a {@code Integer}, handling
     * hex (0xhhhh) and octal (0dddd) notations.
     * N.B. a leading zero means octal; spaces are not trimmed.</p>
     *
     * <p>Returns {@code null} if the string is {@code null}.</p>
     * @param str a {@code String} to convert, may be null
     * @return converted {@code Integer} (or null if the input is null)
     * @throws NumberFormatException if the value cannot be converted
     */
    public static Integer decode(final String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        // decode() handles 0xAABD and 0777 (hex and octal) as well.
        try {
            return Integer.decode(str);
        } catch (Exception exception) {
            return defaultValue;
        }
    }

    public static Integer parseInt(final String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
