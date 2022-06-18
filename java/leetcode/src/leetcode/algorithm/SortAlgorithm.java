package leetcode.algorithm;

import java.util.Arrays;

public class SortAlgorithm {

	public static void main(String[] args) {
		// 数组没有重复的数
		int[] arr = { 5, 3, 4, 6, 7, 2, 8, 1 };
		printArray(arr);
		bubbleSort(arr);
		printArray(arr);
	}

	/**
	 * 选择排序
	 * 
	 * @param arr
	 */
	public static void selectSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int len = arr.length;
		for (int i = 0; i < len; i++) {
			// 当前的最小值的索引
			int minValueIndex = i;
			// 对剩下的n-i个数进行选择排序
			for (int j = i + 1; j < len; j++) {
				minValueIndex = arr[j] < arr[minValueIndex] ? j : minValueIndex;
				swap(arr, i, minValueIndex);
			}
		}
	}

	/**
	 * 冒泡排序
	 * 
	 * @param arr
	 */
	public static void bubbleSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		int len = arr.length;
		// 控制排序的范围
		for (int end = len - 1; end >= 0; end--) {
			// 在指定范围排序 在[0, end]做一件事：比较相邻的两个数并交换
			for (int i = 1; i <= end; i++) {
				if (arr[i - 1] > arr[i]) {
					swap(arr, i - 1, i);
				}
			}
		}
	}

	/**
	 * 插入排序
	 * 
	 * @param arr
	 */
	public static void insertSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		// 0 - 0 有序 默认已完成
		// 0 - 1 有序
		// 0 - 2 有序
		// 0 - N 有序
		int N = arr.length;
		for (int i = 1; i < N; i++) {
			// 新来的数在i位置，外层循环
			int newIndex = i;
			// 往左进行：在每个子区间[0, i]进行排序，循环结束后[0, i]区间是排序好的
			while (i - 1 >= 0 && arr[newIndex - 1] > arr[newIndex]) {
				// 交换
				swap(arr, newIndex - 1, newIndex);
				// 左移一个
				newIndex --;
			}
		}
	}
	
	/**
	 * 插入排序
	 * 
	 * @param arr
	 */
	public static void insertSort2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		// 0 - 0 有序 默认已完成
		// 0 - 1 有序
		// 0 - 2 有序
		// 0 - N 有序
		int N = arr.length;
		for (int i = 1; i < N; i++) {
			// pre新数的前一个位置
			for (int pre = i - 1; pre >= 0 && arr[pre] > arr[pre + 1]; pre--) {
				swap(arr, pre, pre + 1);
			}
		}
	}
	
	
	public static void printArray(int[] arr) {
		System.out.println(Arrays.toString(arr));
	}

	/**
	 * 交换数组的两个值
	 * 
	 * @param arr
	 * @param i
	 * @param j
	 */
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[j];
		arr[j] = arr[i];
		arr[i] = tmp;
	}
}
