package nowcoder.hj;

import java.util.Scanner;

import leetcode.Utils;

/**
 * 【两数之和绝对值最小】给定一个从小到大的有序整数序列(存在正整数和负整数)数组
 * nums，请你在该数组中找出两个数，其和的绝对值(|nums[x]+nums[y]|)为 最小值，并返回
 * 这个绝对值。每种输入只会对一个答案。但是，数组中同一个元素不能使用两遍。
 * 输入描述: 一个通过空格分割的有序整数序列字符串，最多 1000个整数，且整数数值范围是-65535~65535。
 * 输出描述: 两数之和绝对值最小值。
 * @since created on 2022年6月21日
 */
public class HJ2 {

	/**
	 * 测试样例：-1 -3 7 5 11 15
	 * @param args
	 */
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] nums = line.split(" ");
			System.out.println(absMax(Utils.parseInt(nums)));
			break;
		}
		scanner.close();
	}
	
	// 只要得出最小值即可，不需要求出是哪两个数
	
	/**
	 * 方式一：暴力的方式。 遍历所有的两个数的差，记录最小值。 算法的复杂度O(n2)
	 * @param nums
	 * @return
	 */
	public static int absMax(int[] nums) {
		if (nums.length == 1) {
			return nums[0];
		}
		if (nums.length == 2) {
			return nums[0] + nums[1];
		}
		int minValue = nums[0] + nums[1];
		int abs = 0;
		for (int i = 1; i < nums.length - 1; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				// 相邻两个数的绝对值最小值
				abs = Math.abs(nums[i] + nums[j]);
				if (minValue > abs) {
					minValue = abs;
				}
			}
		}
		return minValue;
	}
	
	// 优化方式1: 有问题，不能满足要求
	public static int absMax3(int[] nums) {
		if (nums.length == 1) {
			return nums[0];
		}
		if (nums.length == 2) {
			return nums[0] + nums[1];
		}
		int minValue = nums[0] + nums[1];
		int abs = 0;
		for (int i = 1; i < nums.length - 1; i++) {
			// 有序整数序列
			if (nums[i + 1] < 0 || nums[i] > 0) {
				continue;
			}
			for (int j = i + 1; j < nums.length; j++) {
				// 相邻两个数的绝对值最小值
				abs = Math.abs(nums[i] + nums[j]);
				if (minValue > abs) {
					minValue = abs;
				}
			}
		}
		return minValue;
	}
	
	/**
	 * 两个数要想差的绝对值最小，肯定是需要两个数大小相近。 故有思路：先对数组进行排序，然后遍历一遍，相邻的数相减，记录绝对值最小的数
	 * 时间复杂度：O(nlogn)
	 * @param nums
	 * @return
	 */
	public static int absMax1(int[] nums) {
		return 1;
	}
	
	//遍历一遍数据，找出最大值Max和最小值Min，然后把整个数据进行划分，step=(Max-Min)/n.
	//然后遍历这n个桶，相邻元素的最大值一定是某个桶i中的最大值和桶(i+1)中的最小值的差值。具体如何证明可以自己想想一下。
	//(假如这个相邻元素的最大间距不是某个桶i中的最大值和桶(i+1)中的最小值的差值，即最大间距的两个元素位于同一个桶中，即最大间距小于step，所以Min+n*step<Maxd的。因此矛盾。所以最大元素肯定是位于不同桶中的。)
	//整个算法时间复杂度为O(n)，空间复杂度也是O(n)
	public static int absMax5(int[] nums) {
		return 1;
	}
	
	// 初中学过两个不等式 
	// 在不等式应用中，经常涉及质量、面积、体积等，也涉及某些数学对象（如实数、向量）的大小或绝对值。它们都是通过非负数来度量的。
	// 公式：||a|-|b|| ≤|a±b|≤|a|+|b|
	// 证明此不等式：｜｜a｜－｜b｜｜≤｜a＋b｜≤｜a｜＋｜b｜
	
	
	// 另外一种方式：找到0位置，好像也不行
	public static int absMax6(int[] nums) {
		// 由于有负数也有正数，所以找到第一个大于0的位置
		int index = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] >= 0) {
				index = i;
			}
		}
		// 不论是小于0的一边还是大于0的一边，肯定是靠近0的两个绝对值最小；
		// 也有可能是一个正数 + 一个负数
		return 0;
	}
}
