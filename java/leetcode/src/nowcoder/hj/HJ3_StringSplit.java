package nowcoder.hj;

import java.util.Scanner;

/**
 * 
 * @since created on 2022年6月21日
 */
public class HJ3_StringSplit {

	/**
	 * 【字符串分割】给定非空字符串s，将该字符串分割成一些子串，使每个子串的ASCIIA码值的和均为水仙花数。
	 * 备注:“水仙花数”是指一个三位数，每位上数字的立方和等于该数字本身，如 371是“水仙花数”，因为:371=3^3+7^3+1^3。
	 * 1、若分割不成功则返回 0 2、若分割成功且分割结果不唯一则返回-1 3、若分割成功且分割结果唯一，则返回分割后的子串数目
	 * 
	 * @param args
	 */

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			// 非空字符串，注意什么是子串？子串是相邻的字符 连续的字符串
			String line = scanner.nextLine();
			break;
		}
		StringBuffer buffer = new StringBuffer();
		scanner.close();
	}

	public static int splitString(String str) {
		// 水仙花数是指一个三位数
		if (str.length() % 3 != 0) {
			return 0;
		}
		char[] chars = str.toCharArray();
		int count = 0;
		
		// 回溯
		
		
		
		return 0;
	}

	public static int sumAsciiCode(char ... cs) {
		int sum = 0;
		for (int i = 0; i < cs.length; i++) {
			sum += cs[i];
		}
		return sum;
	}
	
	/**
	 * 是否是水仙花数
	 * 
	 * @param num
	 * @return
	 */
	public static boolean isNarcissisticBumber(int num) {
		// 对10作除法然后取余得到的数为个位数
		int g = num % 10;
		// 对10作除法得到一个两位数，然后对10再取余得到个位数
		int s = (num / 10) % 10;
		// 对100作整除得到一个数，即为百位数
		int b = num / 100;
		int sum = (g * g * g) + (s * s * s) + (b * b * b); // 得到总和
		// 判断是否相等
		if (sum == num) { // 若相等
			System.out.println("该数为水仙花数！");
			return true;
		}
		System.out.println("该数不为水仙花数！");
		return false;
	}
}
