package org.mybatis.generator.internal.util;

import java.util.StringTokenizer;

public class StringUtils {

    /**
     * Utility class. No instances allowed
     */
    private StringUtils() {
        super();
    }

    /**
     * String不为空
     *
     * @param s
     * @return
     */
    public static boolean isNotEmpty(String s) {
        return s != null && s.length() > 0;
    }

    public static String composeFullyQualifiedTableName(String catalog,
                                                        String schema, String tableName, char separator) {
        StringBuilder sb = new StringBuilder();

        if (isNotEmpty(catalog)) {
            sb.append(catalog);
            sb.append(separator);
        }

        if (isNotEmpty(schema)) {
            sb.append(schema);
            sb.append(separator);
        } else {
            if (sb.length() > 0) {
                sb.append(separator);
            }
        }
        sb.append(tableName);
        return sb.toString();
    }

    /**
     * 字符串包含空格
     * @param s
     * @return
     */
    public static boolean containsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }

    /**
     * 一个字符串和多个字符串进行Equals比较，只有有一个为true时返回true，否则返回false
     * @param str
     * @param targets
     * @return
     */
    public static boolean equalsAny(String str, String... targets) {
        for (String target : targets) {
            if (str.equals(target)) {
                return true;
            }
        }
        return false;
    }

    public static String escapeStringForJava(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("\\\""); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    public static String escapeStringForKotlin(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"$", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("\\\""); //$NON-NLS-1$
            } else if ("$".equals(token)) { //$NON-NLS-1$
                sb.append("\\$"); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    public static boolean isTrue(String s) {
        return "true".equalsIgnoreCase(s); //$NON-NLS-1$
    }

    public static boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }
        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }
}
