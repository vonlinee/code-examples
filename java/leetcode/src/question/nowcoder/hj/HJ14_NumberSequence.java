package question.nowcoder.hj;

import java.util.List;

/**
 * https://blog.nowcoder.net/n/cdfc391fce2f4b779a8729b96e22756c
 * <p>
 * 输入一个字符串仅包含大小写字母和数字，求字符串中包含的最长的非严格递增连续输数字序列的长度(比如12234属于非严格递增连续数字序列)
 */
public class HJ14_NumberSequence {

    public static void main(String[] args) {
        play("abc2234019A334bc");
    }

    public static void play(String str) {
        System.out.println(str);
        char[] chars = str.toCharArray();
        int cur_index = -1;
        // 当前最大值
        int max_len = 0;
        // 前一个的最大值
        int prev_max_len = 0;
        while (cur_index < chars.length) {
            cur_index++;
            if (cur_index == chars.length) break;
            // 判断是否是数字
            if (chars[cur_index] < '0' || chars[cur_index] > '9') {
                continue;
            }
            // 当前位置肯定是数字
            int i = cur_index;
            max_len++; // 当前位置算1个
            // 判断是否递增，同时是数字
            while (chars[i] >= '0' && chars[i] <= '9') {
                if (chars[i] <= chars[i + 1]) {
                    max_len++;
                } else {
                    break;
                }
                i++;
                if (i >= chars.length - 1) {
                    break;
                }
            }
            // j位置不是数字，从下一个继续开始
            cur_index = i;
            if (max_len > prev_max_len) {
                prev_max_len = max_len;
            }
            max_len = 0;
        }
        System.out.println("最长的非严格递增连续数字序列的长度 = " + prev_max_len);
    }

    static int max_len = 0;

    /**
     * 递归方式
     * @param str
     * @param i
     * @param j
     */
    public static void play1(String str, int i, int j, List<String> subStrings) {
        int start = i, end = j;

    }
}
