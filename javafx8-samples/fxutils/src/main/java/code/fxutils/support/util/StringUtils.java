package code.fxutils.support.util;

import java.util.StringTokenizer;

public final class StringUtils {

    public static String[] split(String str, String delimeter) {
        StringTokenizer st = new StringTokenizer(str, delimeter);
        int i = st.countTokens();
        String[] strings = new String[i];
        while (st.hasMoreTokens()) {
            strings[i - (++i)] = st.nextToken();
        }
        return strings;
    }

}
