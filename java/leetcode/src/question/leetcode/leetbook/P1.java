package question.leetcode.leetbook;

import java.util.Stack;

public class P1 {

    public int reverse(int x) {
        int res = 0;
        while (x != 0) {
            // 取最低位
            int t = x % 10;
            int newRes = res * 10 + t;
            //如果数字溢出，直接返回0
            if ((newRes - t) / 10 != res) return 0;
            res = newRes;
            x = x / 10;
        }
        return res;
    }

    public int reverse1(int x) {
        Stack<Integer> stack = new Stack<>();
        String s = String.valueOf(x);
        if (x < 0) {
            s = s.substring(1); // 去掉负号
        }
        for (char c : s.toCharArray()) {
            stack.push(Integer.parseInt(String.valueOf(c)));
        }
        double res = 0;
        int len = stack.size(); // 表示几位数
        // 出栈即可
        while (!stack.isEmpty()) {
            // 高位先弹出来
            int i = stack.pop();
            // 高位的结果
            double newRes = i * Math.pow(10, len - 1) + res;
            // 前一次的结果
            res = newRes;
            // 如果溢出直接返回0
            if ((x > 0 && newRes > Integer.MAX_VALUE) || (x < 0 && newRes < Integer.MIN_VALUE)) {
                return 0;
            }
            len--;
        }
        return x < 0 ? -(int) res : (int) res;
    }

    // 错误方法：
    // 因为源字符串中排在后的字符可能在数组中排在前
    public int firstUniqChar(String s) {
        char max = 0;
        char[] chars = s.toCharArray();
        return -1;
    }

    public boolean isPalindrome(String s) {
        // 空字符串定义为有效的回文串
        if (s.length() == 0) return true;
        int i = 0, j = s.length() - 1;
        while (i < j) {
            char ci = s.charAt(i);
            char cj = s.charAt(j);
            // 去除掉不是字母的情况
            if (!isValid(ci)) {
                i++;
                continue;
            }
            if (!isValid(cj)) {
                j--;
                continue;
            }
            // 比较：忽略字母的大小写 只考虑字母和数字字符
            // 数字比较
            if (isNumber(ci) && isNumber(cj)) {
                if (ci != cj) return false;
            } else if (isAlpha(ci) && isAlpha(cj)) {
                // 都是字母，忽略大小写
                if (ci != cj && !(ci - 32 == cj || ci + 32 == cj)) {
                    return false;
                }
            } else {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }

    public boolean isValid(char c) {
        return isAlpha(c) || isNumber(c);
    }

    public boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public boolean isNumber(char c) {
        return '0' <= c && c <= '9';
    }

    public int myAtoi(String s) {
        char[] chars = s.toCharArray();
        char[] arr = new char[chars.length];
        // 正负
        boolean positive = true;
        // 是否已经确定正负
        boolean flag = false;
        // 记录数字的索引，已有几个数字
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            // 去掉空格
            if (chars[i] == ' ') {
                if (index > 0 || flag) {
                    break;
                }
                continue;
            }
            // 检查空格后的下一个字符
            if (!flag) {
                if (chars[i] == '-') {
                    positive = false;
                    flag = true;
                    continue;
                }
                if (chars[i] == '+') {
                    flag = true;
                    continue;
                }
                if (isNumber(chars[i])) { // 空格之后有可能没有+/-符号，直接是数字
                    flag = true;
                    arr[index++] = chars[i];
                    continue;
                }
                // 是字母或者其他字符，反正是非数字
                return 0;
            }
            // 不为数字则跳过
            if (!isNumber(chars[i])) {
                // 读入下一个字符，直到到达下一个非数字字符或到达输入的结尾。字符串的其余部分将被忽略。
                break;
            }
            arr[index++] = chars[i];
        }
        // 拼接字符串
        int res = 0;
        if (index == 0) return 0;
        for (int i = 0; i < index; i++) {
            int num = arr[i] - '0';
            // 高位 * 10 ^ n + 前面的结果
            // index表示有几位数字
            // 这里没有管正负号
            double newRes = num * Math.pow(10, index - i - 1) + res;
            if (newRes < Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            if (newRes > Integer.MAX_VALUE) {
                // 如果原来的数是负数
                if (!positive) return Integer.MIN_VALUE;
                return Integer.MAX_VALUE;
            }
            res = (int) newRes;
        }
        return positive ? res : -res;
    }

    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        // 不存在该子字符串
        if (haystack.length() < needle.length()) return -1;
        char[] chars1 = haystack.toCharArray();
        char[] chars2 = needle.toCharArray();
        int i = 0, j = 0;
        // 回溯到的位置
        int backIndex = 0;
        while (i <= haystack.length() - needle.length()) {
            // 记录开始比较的位置，匹配失败后进行回溯
            backIndex = i;
            // 相等则比较下一个
            while (j < needle.length()) {
                if (chars1[i] != chars2[j]) {
                    j = 0;
                    break;
                }
                i++;
                j++;
            }
            if (j == needle.length()) {
                // 匹配成功
                return backIndex;
            }
            // 匹配失败
            // 回溯到开始比较的下一个位置
            i = backIndex + 1;
        }
        if (i > haystack.length() - needle.length()) return -1;
        return i;
    }

    public String countAndSay(int n) {
        if (n == 1) return "1";
        String prev = countAndSay(n - 1);
        // 描述前一项的逻辑
        StringBuilder result = new StringBuilder();
        // 要描述一个数字字符串，首先要将字符串分割为 最小 数量的组，
        // 每个组都由连续的最多 相同字符 组成。
        // 然后对于每个组，先描述字符的数量，然后描述字符，形成一个描述组。
        // 要将描述转换为数字字符串，先将每组中的字符数量用数字替换，
        // 再将所有描述组连接起来。
        char[] chars = prev.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            int count = 0;
            for (int j = i; j < chars.length; j++) {
                if (chars[i] != chars[j]) {
                    break;
                }
                count++;
            }
            i += count - 1;
            result.append(count).append(chars[i]);
        }
        return result.toString();
    }

    // 查找字符串数组中的最长公共前缀
    public String longestCommonPrefix(String[] strs) {
        int m = 0;
        // 先按长度排序
        int minLen = strs[0].length();
        String minLenStr = strs[0];
        for (int i = 1; i < strs.length; i++) {
            if (strs[i].length() <= minLen) {
                minLen = strs[i].length();
                minLenStr = strs[i];
            }
        }
        // 找到最长前缀
        char[] chars = minLenStr.toCharArray();

        String result = "";
        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < strs.length; j++) {
                if (strs[j].charAt(i) != chars[i]) {
                    break;
                }
                m++;
            }
            // 当前字符相同
            if (m != strs.length) {
                break;
            }
            result += chars[i];
            m = 0;
        }
        return result;
    }

    // 错误方法：
// 因为源字符串中排在后的字符可能在数组中排在前
    public int firstUniqChar1(String s) {
        char max = 0;
        // 找到最大值
        char[] chars = s.toCharArray();
        for (char c : chars) {
            if (c > max) max = c;
        }
        // 统计
        int[] arr = new int[max + 1];
        for (int i = 0; i < chars.length; i++) {
            arr[chars[i]]++;
        }
        // 找到只有1个的值
        int value = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                value = i;
            }
        }
        // 在原数组中找到索引
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == (char) value) {
                index = i;
                return index;
            }
        }
        return -1;
    }

    public int firstUniqChar2(String s) {
        char[] chars = s.toCharArray();

        // 统计每个字符出现的次数： s 只包含小写字母
        int[] counts = new int[26]; // 26个
        for (int i = 0; i < chars.length; i++) {
            int index = chars[i] - 'a';
            counts[index]++;
        }
        // 从前往后遍历字符串s中的每个字符，如果某个字符出现一次直接返回
        for (int i = 0; i < chars.length; i++) {
            if (counts[chars[i] - 'a'] == 1) return i;
        }
        // 找到 它的第一个不重复的字符
        return -1;
    }

    public boolean isAnagram(String s, String t) {
        char[] chars1 = s.toCharArray();
        // 统计每个字符出现的次数： s 只包含小写字母
        int[] counts1 = new int[26]; // 26个
        for (int i = 0; i < chars1.length; i++) {
            int index = chars1[i] - 'a';
            counts1[index]++;
        }
        char[] chars2 = t.toCharArray();
        // 统计每个字符出现的次数： s 只包含小写字母
        int[] counts2 = new int[26]; // 26个
        for (int i = 0; i < chars2.length; i++) {
            int index = chars2[i] - 'a';
            counts2[index]++;
        }
        // 比较是否统计数字相同
        for (int i = 0; i < counts1.length; i++) {
            if (counts1[i] != counts2[i]) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        P1 p = new P1();

        boolean reverse = p.isAnagram("anagram", "nagaram");
        System.out.println(reverse);
    }

}
