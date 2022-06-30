package leetcode.algorithm.recursive;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 子串和子序列问题
 */
public class C03_SubStringSequence {

    public static void main(String[] args) {
        List<String> abc = subs("ABC");

        System.out.println(abc);
    }

    /**
     * 求字符串的所有子序列
     * @param s
     * @return
     */
    public static List<String> subs(String s) {
        char[] str = s.toCharArray();
        String path = "";
        ArrayList<String> ans = new ArrayList<>();
        process1(str, 0, ans, path);
        return ans;
    }

    /**
     * @param str   固定不变
     * @param index index此时来到的位置，要或不要
     * @param ans   如果index来到了str的终止位置，把沿途路径形成的答案，放入ans中
     * @param path  之前做出的选择就是path
     */
    public static void process1(char[] str, int index, List<String> ans, String path) {
        if (index == str.length) {
            ans.add(path);
            return;
        }
        // 引用传递不改变外部的值
        // 第一层递归就表示不要当前索引位置的字符，直接index + 1跳到下一个字符
        process1(str, index + 1, ans, path);
        // 这层递归就表示要当前索引位置的字符，进行拼接
        String yes = path + str[index];
        process1(str, index + 1, ans, yes);
    }

    public static List<String> testFullPermutation(String str) {
        List<String> res = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return res;
        }
        char[] chs = str.toCharArray();
        fullPermutation(chs, 0, res);
        return res;
    }

    /**
     * @param str str[0 ... i-1]都是已经决定好的
     * @param i   当前位置
     * @param ans 保存结果
     */
    public static void fullPermutation(char[] str, int i, List<String> ans) {
        if (i == str.length) {
            ans.add(String.valueOf(str));
        }
        // 如果i没有终止，只有i后面的字符可以来到i位置
        for (int j = i; j < str.length; j++) { // i后面的字符都有机会
            swap(str, i, j);
            fullPermutation(str, j + 1, ans);
            swap(str, i, j); // 还原，因为经过上面的递归，又到了分支树的根节点
        }
    }

    /**
     * @param str str[0 ... i-1]都是已经决定好的
     * @param i   当前位置
     * @param ans 保存结果
     */
    public static void fullPermutation1(char[] str, int i, Set<String> ans) {
        if (i == str.length) {
            ans.add(String.valueOf(str));
        }
        // 如果i没有终止，只有i后面的字符可以来到i位置
        for (int j = i; j < str.length; j++) { // i后面的字符都有机会
            swap(str, i, j);
            fullPermutation1(str, j + 1, ans);
            swap(str, i, j); // 还原，因为经过上面的递归，又到了分支树的根节点
        }
    }

    //交换数组m位置和n位置上的值
    public static void swap(char[] arrayA, int m, int n) {
        char temp = arrayA[m];
        arrayA[m] = arrayA[n];
        arrayA[n] = temp;
    }

    public static void permutation(char[] str, int i, List<String> ans) {
        if (i == str.length) {
            ans.add(String.valueOf(str));
            return;
        }
        // Hash表
        boolean[] visit = new boolean[26];
        for (int j = i; j < str.length; j++) {
            if (!visit[str[j] - 'a']) {
                visit[str[j] - 'a'] = true;
                swap(str, i, j);
                permutation(str, i + 1, ans);
                swap(str, i, j);
            }
        }
    }
}
