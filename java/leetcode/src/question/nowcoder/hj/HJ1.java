package question.nowcoder.hj;

import java.util.Scanner;

/**
 * 字符串最后一个单词的长度
 * 
 * @since created on 2022年6月21日
 */
public class HJ1 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// 注意 hasNext 和 hasNextLine 的区别
		String str = "";
		while (in.hasNextLine()) {
			str = in.nextLine();
			break;
		}
		System.out.println(lengthOfLastWord(str));
	}

	/**
	 * 可以从右边开始，找到第一个空格即可
	 * @param str
	 * @return
	 */
	public static int lengthOfLastWord(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		char[] arr = str.toCharArray();
		int blankIndex = -1;
		for (int i = 0; i < arr.length; i++) {
			if (' ' == arr[i]) {
				blankIndex = i;
			}
		}
		return arr.length - blankIndex - 1;
	}
}
