package io.devpl.codegen.utils;

public class Utils {

    public static String removeInvalidCharacters(String str) {
        if (str == null || str.length() == 0) {
            return str;
        }
        return str.trim().replace("\n", "").replace("\t", "");
    }
}
