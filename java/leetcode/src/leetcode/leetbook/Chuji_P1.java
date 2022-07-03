package leetcode.leetbook;

import sun.java2d.windows.GDIWindowSurfaceData;

import java.util.*;

/**
 * https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2gy9m/
 */
public class Chuji_P1 {

    // 自己写的：通过测试用例：188 / 361
    public int removeDuplicates(int[] nums) {
        int i = 0;
        if (nums.length <= 1) {
            return nums.length;
        }
        for (int k = 0; k < nums.length; k++) {
            int j = k + 1;
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

    /**
     * 这个算法仅限用于去除相邻重复的数据，无法去除不相邻但数字相同的数组
     * @param nums
     * @return
     */
    public int removeDuplicates1(int[] nums) {
        // 这里这个变量不是必须的
        int len = -1;
        if (nums.length < 2) {
            return nums.length;
        }
        int left = 0, right = 1;
        // 始终是right > left，因此只需要right作为条件即可
        while (left < nums.length) {
            while (right < nums.length && nums[left] == nums[right]) {
                right++;
            }
            // 每次填充一个
            nums[++len] = nums[left];
            left = right;
            right = left + 1;
        }
        return len + 1;
    }

    public int[] intersect(int[] nums1, int[] nums2) {
        // 先排序
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> list = new ArrayList<>();
        int i = 0, j = 0, index = 0;
        // 要求一定要先排序
        while (i < nums1.length && j < nums2.length) {
            // 交集元素
            if (nums1[i] == nums2[j]) {
                list.add(nums1[i]);
                // 两个指针都向后移动一个
                i++;
                j++;
            } else {
                // 较小的向后移动，因为已经按升序排好序了
                int i1 = nums1[i] > nums2[j] ? j++ : i++;
            }
        }
        int[] arr = new int[list.size()];
        for (int i1 = 0; i1 < list.size(); i1++) {
            arr[i1] = list.get(i1);
        }
        return arr;
    }

    public int[] plusOne(int[] digits) {
        int index = digits.length;
        while (index > 0) {
            index--;
            // 当前位加1需要进位
            if (digits[index] + 1 <= 9) {
                digits[index]++; // 当前位加1即可
                return digits;
            }
            // 当前位肯定为0
            digits[index] = 0;
            // 当前位的下一位需要进行+1
        }
        // 最高位需要进位
        int[] newArr = new int[digits.length + 1];
        newArr[0] = 1;
        Arrays.fill(newArr, 1, newArr.length, 0);
        return newArr;
    }

    public void moveZeroes(int[] nums) {
        int zero_index = 0; // 速度为1
        int no_zero_index = 0;
        while (zero_index < nums.length) {
            if (nums[zero_index] == 0) {
                no_zero_index = zero_index + 1;
                while (no_zero_index < nums.length) {
                    // 找到第一个非0的数
                    if (nums[no_zero_index] != 0) {
                        // 将两个指针位置的数组交换，这里不会影响非0值的相对顺序
                        // 因为zero_index~no_zero_index中间都是0
                        nums[zero_index] = nums[no_zero_index];
                        nums[no_zero_index] = 0;
                        break;
                    }
                    no_zero_index++;
                }
            }
            // 此时zero_index肯定是非0值
            zero_index++;
        }
    }

    // 注意还有负数
    public int[] twoSum(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        int[] result = new int[2];
        // 先排序
        Arrays.sort(nums);
        int mid = 0;
        // 二分
        while (left < right) {
            mid = (left + right) / 2;
            // 确定右边界
            if (nums[mid] > target / 2) {
                right = mid;
            }
            // 确定左边界
            if (nums[mid] < target / 2) {
                left = mid;
            }
            if (nums[left] + nums[right] == target) {
                result[0] = map.get(nums[left]);
                result[1] = map.get(nums[right]);
                return result;
            }
            left++;
        }
        return result;
    }

    public int[] twoSum1(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        int index = 0;
        while (map.size() != 2) {
            // 两数之和，必然两个数都小于target
            if (nums[index] > target) continue;
            // 为空时直接放进去
            if (map.isEmpty()) {
                map.put(index, nums[index]);
                continue;
            }
            // 集合不为空，只有一个元素
            int num = (int) map.values().toArray()[0];
            if (num + nums[index] == target) {
                map.put(index, nums[index]);
                break;
            }
            index = ++index % nums.length;
        }
        return null;
    }

    public int[] twoSum2(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        // 先排序
        Arrays.sort(nums);
        int mid = 0;
        // 二分
        while (left < right) {
            mid = (left + right) / 2;
            // 确定右边界
            if (nums[mid] > target / 2) {
                right = mid;
            }
            // 确定左边界
            if (nums[mid] < target / 2) {
                left = mid;
            }
            if (nums[left] + nums[right] == target) {
                break;
            }
        }
        int[] result = new int[2];
        result[0] = map.get(nums[left]);
        result[1] = map.get(nums[right]);
        return result;
    }

    public boolean isValidSudoku(char[][] board) {

        return true;
    }

    public static void main(String[] args) {
        Chuji_P1 test = new Chuji_P1();
        int[] arr1 = {2, 7, 11, 15};
        System.out.println(Arrays.toString(test.twoSum1(arr1, 21)));
    }

    public void rotate1(int[][] matrix) {
        //

        int rowCount = matrix.length;
        int colCount = matrix[0].length;

        int N = matrix.length;
        // 圈数
        int cirCount = rowCount / 2;

        for (int i = 0; i < cirCount; i++) {
            // 每圈
            // 从左上角开始，按顺时针方向

            // 右下角是
            int j = matrix[N - i - 1][N - i - 1];
            int x = 0, y = 0;
        }
    }

    public void rotate(int[][] matrix) {
        int length = matrix.length;
        //因为是对称的，只需要计算循环前半行即可
        // 这里i实际上就是第i圈
        for (int i = 0; i < length / 2; i++) {
            // 循环次数只有一条边的长度
            for (int j = i; j < length - i - 1; j++) {
                // 当前这点的值
                int temp = matrix[i][j];
                // 计算当前点要换到的位置
                int m = length - j - 1;
                int n = length - i - 1;
                // 一次交换必须把四个数都换掉
                matrix[i][j] = matrix[m][i];
                matrix[m][i] = matrix[n][m];
                matrix[n][m] = matrix[j][n];
                matrix[j][n] = temp;
            }
        }
    }

    public void reverseString(char[] s) {
        // 如果为偶数，那么中间字符是 s/2 和 s/2 - 1
        // 这里无论奇数偶数都一样，都是以mid为界，将左右两边对称
        int mid = s.length / 2;
        char temp;
        // 空间复杂度O(1)
        for (int i = 0; i <= mid - 1; i++) {
            // 交换 i 和 s.length - i - 1位置上的值
            temp = s[i];
            s[i] = s[s.length - i - 1];
            s[s.length - i - 1] = temp;
        }
    }

    public int reverse(int x) {
        String s = String.valueOf(Math.abs(x));
        char[] chars = s.toCharArray();
        // 反转字符串
        int mid = chars.length / 2;
        char temp;
        // 空间复杂度O(1)
        for (int i = 0; i <= mid - 1; i++) {
            // 交换 i 和 s.length - i - 1位置上的值
            temp = chars[i];
            chars[i] = chars[chars.length - i - 1];
            chars[chars.length - i - 1] = temp;
        }
        String s2 = x < 0 ? "-" + String.valueOf(chars) : String.valueOf(chars);
        // 验证结果是否满足32位数的范围
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE;
        //
        if (x < 0) {
            // 是否大于Integer.MIN_VALUE
            String minValue = String.valueOf(Integer.MIN_VALUE);
            int compare = compare(s2, minValue);
        }
        return Integer.parseInt(s2);
    }

    public int compare(String num1, String num2) {
        // 如果长度不等，那么肯定长的那个大一些
        if (num1.length() != num2.length()) {
            int l1 = num1.length();
            int l2 = num2.length();
            return l1 > l2 ? 1 : -1;
        }
        // 如果长度相等，每位进行比较
        char[] chars1 = num1.toCharArray();
        char[] chars2 = num2.toCharArray();
        for (int i = 0; i < chars1.length; i++) {
            // 相等则继续比较下一位
            if (chars1[i] == chars2[i]) continue;
            //
            return chars1[i] > chars2[i] ? 1 : -1;
        }
        return 0;
    }




}
