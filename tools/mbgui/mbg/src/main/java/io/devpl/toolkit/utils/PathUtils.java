package io.devpl.toolkit.utils;

import com.google.common.base.Strings;

import java.io.File;

public class PathUtils {

    public static String join(String... paths) {
        StringBuilder tmp = new StringBuilder();
        for (String path : paths) {
            if (StringUtils.hasText(path)) {
                tmp.append(path);
                tmp.append(File.separator);
            }
        }
        return tmp.deleteCharAt(tmp.lastIndexOf(File.separator)).toString();
    }

    public static String joinPackage(String... packages) {
        StringBuilder tmp = new StringBuilder();
        for (String aPackage : packages) {
            if (!Strings.isNullOrEmpty(aPackage)) {
                tmp.append(aPackage);
                tmp.append(".");
            }
        }
        return tmp.deleteCharAt(tmp.lastIndexOf(".")).toString();
    }

    public static String getShortNameFromFullRef(String ref) {
        if (Strings.isNullOrEmpty(ref)) {
            return "";
        }
        if (!ref.contains(".")) {
            return ref;
        }
        return ref.substring(ref.lastIndexOf(".") + 1);
    }
}
