package nowcoder.hj;

import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.nowcoder.net/n/d8767dfa146a48f194a32c970f14b530
 * <p>
 * 定义:当一个字符串只有元音字母(aeiouAEIOU)组成，称为元音字符串。现给定一个字符串，请找出其中最长的元音字符串，并返回其长度;
 * 如果找不到，则返回 0。子串:字符串中任意一个连续的字符组成的子序列称为该字符串的子串。
 */
public class HJ9_LongestVowelSubstring {

    public static void main(String[] args) {
        longest("asdbuiodevauufgh");
    }

    // 找出其中最长的元音字符串
    // aeiouAEIOU
    public static void longest(String str) {
        char[] chars = str.toCharArray();
        int len = str.length();
        List<String> strList = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        // 类似indexOf的实现
        for (int i = 0; i < len; i++) {
            // 是元音字母
            if ("aeiouAEIOU".indexOf(String.valueOf(chars[i])) > 0) {
                s.append(chars[i]);
            } else {
                // 如果不是元音字母，则保存已经记录的元音子串
                String s1 = s.toString();
                if (s1.length() > 0) {
                    strList.add(s1);
                    // 先清空，再重新存储新的
                    s.delete(0, s.length());
                }
            }
        }
        System.out.println(strList);
    }

}
