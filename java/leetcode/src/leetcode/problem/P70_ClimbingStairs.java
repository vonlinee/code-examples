package leetcode.problem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * https://leetcode.cn/problems/climbing-stairs/
 */
public class P70_ClimbingStairs {

    public static void main(String[] args) {
        P70_ClimbingStairs test = new P70_ClimbingStairs();
//        System.out.println(test.climbStairs(3));

        System.out.println(test.removeDuplicates("abbaca"));
    }

    // 超出时间限制 , N的范围【1，45】
    public int climbStairs(int N) {
        // base case
        if (N == 1) return 1;
        if (N == 2) return 2;
        return climbStairs(N - 1) + climbStairs(N - 2);
    }

    public int climbStairs1(int N) {
        return 0;
    }

//    public String removeDuplicates(String s) {
//        char[] arr = s.toCharArray();
//        int i = -1, j = 0;
//        while (j < arr.length) {
//            i++;
//            j = i + 1;
//            while (j < arr.length - 1 && arr[j] == 0) {
//                j++;
//            }
//            if (arr[i] == arr[j]) {
//                arr[i] = arr[j] = 0;
//            }
//            // 判断是否有重复
//        }
//        return "";
//    }

    public String removeDuplicates(String s) {
        List<String> chars = new ArrayList<>(s.length());
        for (int i = 0; i < s.length(); i++) {
            chars.add(String.valueOf(s.charAt(i)));
        }
        while (hasDuplicated(chars)) {
            for (int i = 0; i < chars.size() - 1; i++) {
                if (chars.get(i).equals(chars.get(i + 1))) {
                    chars.set(i, "");
                    chars.set(i + 1, "");
                }
            }
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < chars.size() - 1; i++) {
            res.append(chars.get(i));
        }
        return res.toString();
    }

    public boolean hasDuplicated(List<String> strings) {
        for (int i = 0; i < strings.size() - 1; i++) {
            if (strings.get(i).equals(strings.get(i + 1))) {
                return true;
            }
        }
        return false;
    }
}

