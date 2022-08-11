package question.leetcode.problem;

/**
 * https://leetcode.cn/problems/remove-element/
 * 
 * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。 不要使用额外的数组空间，你必须仅使用
 * O(1) 额外空间并 原地 修改输入数组。 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 * 
 * @author Administrator
 *
 */
public class P27_RemoveElement {

	public int removeElement(int[] nums, int val) {
		int num = nums.length;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == val) {
				num --;
				move(nums, val, i);
			}
		}
		return num;
	}
	
	private void move(int[] nums, int val, int index) {
		int temp;
		for (int i = index; i < nums.length - 1; i++) {
			// 将index位置后面的元素向前移一个
			temp = nums[i];
			nums[i] = nums[i + 1];
			nums[i + 1] = temp;
		}
	}
	
	private void output(int[] nums, int val, int len) {
		int count = 0;
		for (int i = 0; i < nums.length && count <= len; i++) {
			if (nums[i] == val) {
				continue;
			}
			count ++;
			System.out.print(nums[i] + " ");
		}
	}
	
	public static void main(String[] args) {
		
		int[] input = {3,2,2,3};
		
		P27_RemoveElement test = new P27_RemoveElement();
		
		int newLen = test.removeElement(input, 3);
		
		test.output(input, 3, newLen);
	}
}
