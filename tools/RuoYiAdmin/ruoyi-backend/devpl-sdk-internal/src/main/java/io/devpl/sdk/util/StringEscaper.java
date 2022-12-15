package io.devpl.sdk.util;

/**
 * StringEscaper ，数据库字符串转义
 */
public class StringEscaper {

    /**
     * <p>
     * 字符串是否需要转义
     * </p>
     * @param str
     * @param len
     * @return
     */
    private static boolean isEscapeNeededForString(String str, int len) {
        boolean needsHexEscape = false;
        for (int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            if (isNeedEscaped(c)) {
                // no need to scan more
                needsHexEscape = true;
                break;
            }
        }
        return needsHexEscape;
    }

    private static boolean isNeedEscaped(char c) {
        switch (c) {
            /* Must be escaped for 'mysql' */
            case 0:
                return true;
            /* Must be escaped for logs */
            case '\n':
                return true;
            case '\r':
                return true;
            case '\\':
                return true;
            case '\'':
                return true;
            /* Better safe than sorry */
            case '"':
                return true;
            /* This gives problems on Win32 */
            case '\032':
                return true;
            default:
                return false;
        }
    }

    /**
     * 转义字符串
     * @param escapeStr
     * @return
     */
    public static String escapeString(String escapeStr) {
        if (escapeStr.matches("\'(.+)\'")) {
            escapeStr = escapeStr.substring(1, escapeStr.length() - 1);
        }
        String parameterAsString = escapeStr;
        int stringLength = escapeStr.length();
        if (isEscapeNeededForString(escapeStr, stringLength)) {
            StringBuilder buf = new StringBuilder((int) (escapeStr.length() * 1.1));
            //
            // Note: buf.append(char) is _faster_ than appending in blocks,
            // because the block append requires a System.arraycopy().... go figure...
            //
            for (int i = 0; i < stringLength; ++i) {
                char c = escapeStr.charAt(i);
                switch (c) {
                    /* Must be escaped for 'mysql' */
                    case 0:
                        buf.append('\\');
                        buf.append('0');
                        break;
                    /* Must be escaped for logs */
                    case '\n':
                        buf.append('\\');
                        buf.append('n');
                        break;
                    case '\r':
                        buf.append('\\');
                        buf.append('r');
                        break;
                    case '\\':
                        buf.append('\\');
                        buf.append('\\');
                        break;
                    case '\'':
                        buf.append('\\');
                        buf.append('\'');
                        break;
                    /* Better safe than sorry */
                    case '"':
                        buf.append('\\');
                        buf.append('"');
                        break;
                    /* This gives problems on Win32 */
                    case '\032':
                        buf.append('\\');
                        buf.append('Z');
                        break;
                    default:
                        buf.append(c);
                }
            }
            parameterAsString = buf.toString();
        }
        return "'" + parameterAsString + "'";
    }
}
