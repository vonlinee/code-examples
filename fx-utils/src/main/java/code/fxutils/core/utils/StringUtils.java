package code.fxutils.core.utils;

import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isBlank(String str) {
        return false;
    }

    /**
     * @param strings
     * @return List<String>
     */
    public static List<String> uniformLength(List<String> strings) {
        int maxLen = strings.get(0).length();
        int size = strings.size();
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size; j++) {
                int nextLen = strings.get(j).length();
                if (nextLen > maxLen) {
                    maxLen = nextLen;
                }
            }
        }
        for (int i = 0; i < size; i++) {
            strings.add(i, appendBlank(strings.remove(i), maxLen));
        }
        return strings;
    }

    /**
     * @param sequence
     * @param c
     * @param len
     * @return String
     */
    public static String append(String sequence, char c, int len) {
        int i = len - sequence.length();
        if (i > 0) {
            for (int j = 0; j < i; j++) {
                sequence += c;
            }
        }
        return sequence;
    }

    /**
     * @param sequence
     * @param len
     * @return String
     */
    public static String appendBlank(String sequence, int len) {
        int i = len - sequence.length();
        if (i > 0) {
            for (int j = 0; j < i; j++) {
                sequence += " ";
            }
        }
        return sequence;
    }

    public static String[] split(String str, String delimeter) {
        StringTokenizer st = new StringTokenizer(str, delimeter);
        int i = st.countTokens();
        String[] strings = new String[i];
        while (st.hasMoreTokens()) {
            strings[i - (++i)] = st.nextToken();
        }
        return strings;
    }

    private static final Pattern ALL_EN_WORDS = Pattern.compile("[a-zA-Z]+");
    private static final Pattern CONTAIN_EN_WORDS = Pattern.compile(".*[a-zA-z].*");

    public static boolean isAllEnWords(String str) {
        return ALL_EN_WORDS.matcher(str).matches();
    }

    public static boolean containEnWords(String str) {
        return CONTAIN_EN_WORDS.matcher(str).matches();
    }

    public static boolean isUpperCase(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isUpperCase(c)) return false;
        }
        return true;
    }

    /**
     * 字符串是否包含中文
     * @param str 待校验字符串
     * @return true 包含中文字符 false 不包含中文字符
     */
    public static boolean containChineseWords(String str) {
        if (str != null && str.length() != 0) {
            Pattern p = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
            Matcher m = p.matcher(str);
            return m.find();
        }
        return false;
    }
}
