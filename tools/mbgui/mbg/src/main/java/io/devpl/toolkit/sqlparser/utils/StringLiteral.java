package io.devpl.toolkit.sqlparser.utils;

import java.util.regex.Pattern;

public class StringLiteral {

    private static final String[] parsePatterns = {"yyyy-MM-dd", "yyyy年MM月dd日", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

    private static final Pattern PATTERN_INT_LITERAL_VALUE = Pattern.compile("^[-+]?[\\d]*$");
    private static final Pattern PATTERN_DOUBLE_LITERAL_VALUE = Pattern.compile("^[-+]?[.\\d]*$");

    public static boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        return PATTERN_DOUBLE_LITERAL_VALUE.matcher(str).matches();
    }
}
