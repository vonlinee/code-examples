package question.leetcode.leetbook.sort;

import java.util.Arrays;

public class Solution {

    public boolean isBadVersion(int version) {
        return version >= 1702766719;
    }

    public int firstBadVersion1(int n) {
        while (isBadVersion(n)) {
            n--;
        }
        return n + 1;
    }

    public int firstBadVersion(int n) {
        // 验证端点
        if (n <= 1) {
            return n;
        }
        if (isBadVersion(n) && !isBadVersion(n - 1)) {
            return n;
        }
        int left = 0, right = n;
        int mid = 1;
        while (left <= right) {
            // 和可能溢出
            mid = left + (right - left) / 2;
            if (mid == 0) {
                mid = right;
            }
            if (isBadVersion(mid)) {
                if (!isBadVersion(mid - 1)) {
                    return mid;
                }
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return mid;
    }

    public void merge1(int[] nums1, int m, int[] nums2, int n) {

        // 非递减序列

        int i = 0;
        int j = 0;
        int k = 0;
        while (i < m || j < n) {

            // 找到第二个数组中小于nums1[i]的第一个数
            while (nums2[j] > nums1[i]) {
                j++;
            }

            // 将[k, j]之间插入到i之前

            // 1.将nums1中元素从i移动j-k+1个位置
            int temp = nums1[k];
            for (int l = k + 1; l < j; l++) {
                swap(nums1, k, l);
            }
            nums1[j] = temp;
            // 2.插入值
            for (int l = k; l < j; l++) {
                nums1[l] = nums2[k];
            }
        }
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int end = m + n - 1;
        while (j >= 0) {
            nums1[end--] = (i >= 0 && nums1[i] > nums2[j]) ? nums1[i--] : nums2[j--];
        }
    }

    /**
     * https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnumcr/
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void mergeArray(int[] nums1, int m, int[] nums2, int n) {
        // 从后往前
        int i = m - 1;
        int j = n - 1;
        int end = m + n - 1;
        // 归并排序
        while (j >= 0) {
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[end] = nums1[i];
            } else {
                nums1[end] = nums2[j];
            }
            end--;
            i--;
            j--;
        }
    }

    public void mergeArray3(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int end = m + n - 1;
        while (j >= 0) {
            nums1[end--] = (i >= 0 && nums1[i] > nums2[j]) ? nums1[i--] : nums2[j--];
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        // nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3

        int[] arr1 = {1, 2, 3, 0, 0, 0};
        int[] arr2 = {2, 5, 6};

        solution.mergeArray3(arr1, 3, arr2, 3);
        System.out.println(Arrays.toString(arr1));
    }
}