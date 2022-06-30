package nowcoder.hj;

import java.util.List;

public class HJ8_LongestShunzi {

    public static void main(String[] args) {
        System.out.println((int) 'A');
    }

    // 顺子3-A
    public static void longestShunzi(List<String> cards1, List<String> cards2) {
        // 除去2和大小王，记录3-A每种牌可能有的张数
        int[] arr = new int[12];
        for (int i = 0, len = arr.length; i < len; i++)
            arr[i] = 4;
        // 遍历已有的牌
        for (String s : cards1) {
            // 一个一个统计
        }
        // 遍历出过的牌
        for (String s : cards2) {
            // 一个一个统计
        }
        // 找到arr中连续不为0的牌的序列即为顺子的长度
        int index = 0;
        for (int i = 0; i < arr.length; i++) {

        }
    }

    // J - 74 Q - 81 K - 75 A - 65
    public static void longestShunzi1(List<String> cards1, List<String> cards2) {
        // 先汇总
        cards1.addAll(cards2);
        // 统计
        int len = cards1.size();
        for (int i = 0; i < len; i++) {
            char s = cards1.get(i).charAt(0);
        }
    }

    public static int calculateIndex(char c) {
        return 1;
    }
}
