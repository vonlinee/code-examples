package io.devpl.tookit.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

public class TypeUtils {

    public static Object stringToNullableTarget(String string, Class<?> t) throws Exception {
        return string == null ? null : t.getConstructor(String.class).newInstance(string);
    }

    public static Object stringToTarget(String string, Class<?> t) throws Exception {
        boolean nullOrEmpty = StringUtils.isEmpty(string);
        if (double.class.equals(t)) {
            return nullOrEmpty ? 0 : Double.parseDouble(string);
        } else if (long.class.equals(t)) {
            return nullOrEmpty ? 0 : Long.parseLong(string);
        } else if (int.class.equals(t)) {
            return nullOrEmpty ? 0 : Integer.parseInt(string);
        } else if (float.class.equals(t)) {
            return nullOrEmpty ? 0 : Float.parseFloat(string);
        } else if (short.class.equals(t)) {
            return nullOrEmpty ? 0 : Short.parseShort(string);
        } else if (boolean.class.equals(t)) {
            return nullOrEmpty ? 0 : Boolean.parseBoolean(string);
        } else if (Number.class.isAssignableFrom(t)) {
            return t.getConstructor(String.class).newInstance(nullOrEmpty ? "0" : string);
        } else {
            return nullOrEmpty ? "" : t.getConstructor(String.class).newInstance(string);
        }
    }

    private boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy年MM月dd日", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyyMMdd"};

    public static Date parseDate(String string) {
        if (string == null) {
            return null;
        }
        try {
            return DateUtils.parseDate(string, parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }
}

