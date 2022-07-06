package question.leetcode.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class P {

    public static void main(String[] args) {
        P p = new P();
        int[] arr = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        System.out.println(p.removeDuplicates(arr));

        System.out.println(Arrays.toString(arr));

    }

    public static void test1() {
        int i = 0, j = 0;
        int num = 98230292;
        int[] numbers = new int[String.valueOf(num).length()];
        int index = 0;
        // 只要num大于0就一直循环，先取模运算，取出每位数字的数字
        while (num > 0) {
            numbers[numbers.length - index - 1] = num % 10;
            //取出某一位数字后，立即去掉该位数字
            num /= 10;
            index++;
        }
        System.out.println(Arrays.toString(numbers));
    }

    public String largestNumber(int[] nums) {
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }
        // 排序
        list.sort((o1, o2) -> {
            String i1 = String.valueOf(o1);
            String i2 = String.valueOf(o2);
            // 长度肯定相等的
            String s1 = i1 + i2;
            String s2 = i2 + i1;
            for (int i = 0; i < s1.length(); i++) {
                if (s1.charAt(i) != s2.charAt(i)) {
                    return s2.charAt(i) - s1.charAt(i);
                }
            }
            return 0;
        });
        // 拼接数组返回结果
        StringBuilder result = new StringBuilder();
        for (Integer integer : list) {
            result.append(integer);
        }
        String s = result.toString();
        if (s.charAt(0) == '0') s = "0";
        return s;
    }

    public int removeDuplicates(int[] nums) {
        int i = 0, j = 1;
        if (nums.length <= 1) {
            return nums.length;
        }
        for (int k = 0; k < nums.length; k++) {
            j = k + 1;
            // 最后一个元素
            if (j == nums.length) {
                if (nums[k - 1] != nums[k]) {
                    nums[i] = nums[k];
                }
                break;
            }
            // 找到最后一个相等的数的下标
            while (nums[j] == nums[k]) {
                j++;
                if (j == nums.length) break; // 边界
            }
            // 此时的nums[k]为上一个数
            nums[i] = nums[k];
            i++;
            k = j - 1;
        }
        return i + 1;
    }
}
