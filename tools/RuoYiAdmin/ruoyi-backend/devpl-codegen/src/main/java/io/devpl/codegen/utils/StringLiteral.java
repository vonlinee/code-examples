package io.devpl.codegen.utils;

import java.util.Date;
import java.util.regex.Pattern;

public class StringLiteral {

    private static final String[] parsePatterns = {"yyyy-MM-dd", "yyyy年MM月dd日", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

    public static Date parseDate(String string) {
        if (string == null) {
            return null;
        }
        return null;
    }

    private static final Pattern PATTERN_INT_LITERAL_VALUE = Pattern.compile("^[-+]?[\\d]*$");
    private static final Pattern PATTERN_DOUBLE_LITERAL_VALUE = Pattern.compile("^[-+]?[.\\d]*$");

    public static boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        return PATTERN_INT_LITERAL_VALUE.matcher(str).matches();
    }

    public static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        return PATTERN_DOUBLE_LITERAL_VALUE.matcher(str).matches();
    }
}
