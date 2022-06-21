package leetcode;

import java.util.HashSet;
import java.util.Iterator;

/**
 * 
 * @since created on 2022年6月21日
 */
public class Utils {

	/**
	 * 
	 * \t 是补全（8-前面字符的位数%8）的距离，也就是说前面有1个字符那么在1个字符后输出一个\t,则\t的长度为7个字符长度
	 * 
	 * 将整形的二进制打印出来
	 * 
	 * @param num 整形是32位无符号数， long是64位的
	 */
	public static void printlnBits(int num) {
		for (int i = 31; i > 0; i--) {
			System.out.print((num & (1 << i)) == 0 ? "0" : "1");
		}
		System.out.println(" [" + num + "]");
	}

	public static int[] randomIntArray(int maxLen, int maxValue) {
		return randomIntArray(maxLen, maxValue, true, false);
	}
	
	// 随机数组：长度随机，值随机
	public static int[] randomIntArray(int maxLen, int maxValue, boolean fixedLength) {
		return randomIntArray(maxLen, maxValue, true, fixedLength);
	}

	// 随机数组：长度随机，值随机
	public static int[] randomIntArray(int maxLen, int maxValue, boolean allowRepeatableValue, boolean fixedLength) {
		if (maxLen < 1) {
			maxLen = 1;
		}
		// 长度随机
		// 产生[0, maxLen)范围的随机数，再强转成整数
		int len = fixedLength ? maxLen : (int) (Math.random() * maxLen);
		int arr[] = new int[len];
		if (allowRepeatableValue) {
			for (int i = 0; i < len; i++) {
				arr[i] = (int) (Math.random() * maxValue);
			}
		} else {
			// 产生不重复的数组值
			if (maxValue < maxLen) {
				throw new RuntimeException("[0, " + maxValue + "]的不重复整数值不足" + maxLen + "个！");
			}
			
			HashSet<Integer> set = new HashSet<>();
			for (int i = 0; i < len; i++) {
				while (!set.add((int) (Math.random() * maxValue))) {
					// 重复添加
				}
			}
			Iterator<Integer> it = set.iterator();
			int i = -1;
			while (it.hasNext()) {
				Integer type = it.next();
				arr[++i] = type;
			}
		}
		return arr;
	}

	/*
	 * 判断数组中是否有重复的值
	 */
	public static boolean cheakIsRepeat(int[] array) {
		HashSet<Integer> hashSet = new HashSet<>();
		for (int i = 0; i < array.length; i++) {
			hashSet.add(array[i]);
		}
		if (hashSet.size() == array.length) {
			return true;
		} else {
			return false;
		}
	}

	// 拷贝数组
	public static int[] copyArray(final int[] arr) {
		int newArr[] = new int[arr.length];
		for (int i = 0; i < newArr.length; i++) {
			newArr[i] = arr[i];
		}
		return newArr;
	}

	// 保证输入参数是等长的
	// 验证两个数组是否值相等
	public static boolean arrayEquals(final int[] arr1, final int[] arr2) {
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// 找到给定变量 的二进制等价物
	public static void swap(int[] arr, int i, int j) {
		arr[i] = arr[i] ^ arr[j];
		arr[j] = arr[i] ^ arr[j];
		arr[i] = arr[i] ^ arr[j];
	}

	public static void swap(int x, int y) {
		x = x ^ y;
		y = x ^ y;
		x = x ^ y;
	}

	public static void printlnArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}
	
	public static int[] parseInt(String[] nums) {
		int[] arr = new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			arr[i] = Integer.parseInt(nums[i]);
		}
		return arr;
	}
}
