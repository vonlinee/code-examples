package leetcode.algorithm;

public class FindNumber {

	public static void main(String[] args) {

	}

	// 功能：二分查找
	public static int findNum(int[] arr, int num) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		int L = 0; // 左边界
		int R = arr.length - 1; // 右边界
		int midIndex = 0; // 中点
		while (L <= R) {
			midIndex = (L + R) / 2; // 中点位置
			if (arr[midIndex] == num) {
				return midIndex;
			} else if (arr[midIndex] < num) { // 左侧不要
				L = midIndex + 1;
			} else if (arr[midIndex] > num) { // 右侧不要
				R = midIndex - 1;
			}
		}
		return -1;
	}

	// 升序
	// 功能：有序数组中找到>=num最左的位置
	public static int findNum1(int[] arr, int num) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		int L = 0; // 左边界
		int R = arr.length - 1; // 右边界
		int midIndex = 0; // 中点
		int resultIndex = -1;
		while (L <= R) {
			midIndex = (L + R) / 2; // 中点位置
			// 满足条件不能直接返回
			if (arr[midIndex] >= num) { // 此时往左边继续二分，右边不要
				resultIndex = midIndex; // 更新缓存的每步的结果
				R = midIndex - 1;
			} else {
				L = midIndex; // 左边不要，继续二分，不更新resultIndex
			}
		}
		return resultIndex;
	}

	// 有序数组中找到<=num最右的位置
	// 自己写

	// arr整体无序，但是相邻数字不相等
	// 局部最小值问题：找到一个局部最小值即可
	public static int oneMinIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		if (arr.length == 1) {
			return arr[0];
		}
		// 长度 = 2
		int N = arr.length;
		if (arr[0] < arr[1]) {
			return 0;
		}
		if (arr[N - 2] > arr[N - 1]) {
			return N - 1;
		}
		// 长度 > 2
		int L = 0;
		int R = N - 1;
		int ans = -1;
		// 注意边界判断
		// 这里是有问题的代码
		while (L <= R) {
			int mid = (L + R) / 2; // 中点
			if (arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1]) {
				ans = mid;
				break;
			} else {
				// 可以将这三个分支合到一起
				// 左边大右边小
				if (arr[mid] < arr[mid - 1] && arr[mid] > arr[mid + 1]) {
					L = mid + 1;
				}
				// 左边小右边大
				if (arr[mid] > arr[mid - 1] && arr[mid] < arr[mid + 1]) {
					R = mid - 1;
				}
				// 左右都小
				if (arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) {
					R = mid - 1;
				}
			}
		}
		return ans;
	}
	
	// 上面的修正版本
	// arr整体无序，但是相邻数字不相等
	// 局部最小值问题：找到一个局部最小值即可
	public static int oneMinIndex1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1;
		}
		if (arr.length == 1) {
			return arr[0];
		}
		// 长度 = 2
		int N = arr.length;
		if (arr[0] < arr[1]) {
			return 0;
		}
		if (arr[N - 2] > arr[N - 1]) {
			return N - 1;
		}
		// 长度 > 2
		int L = 0;
		int R = N - 1;
		int ans = -1;
		// 注意边界判断
		while (L < R - 1) { // FIX
			int mid = (L + R) / 2; // 中点
			if (arr[mid] < arr[mid - 1] && arr[mid] < arr[mid + 1]) {
				ans = mid;
				// break;
				return ans;
			} else {
				// 可以将这三个分支合到一起
				// 左边大右边小
				if (arr[mid] < arr[mid - 1] && arr[mid] > arr[mid + 1]) {
					L = mid + 1;
				}
				// 左边小右边大
				if (arr[mid] > arr[mid - 1] && arr[mid] < arr[mid + 1]) {
					R = mid - 1;
				}
				// 左右都小
				if (arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) {
					R = mid - 1;
				}
			}
		}
		return arr[L] < arr[R] ? L : R;
	}
	
	
	
	// 用于测试
	public static boolean check(int[] arr, int minIndex) {
		if (arr.length == 0) {
			return minIndex == -1;
		}
		int left = minIndex - 1;
		int right = minIndex + 1;
		// 如果越界了那么直接返回true
		boolean leftBigger = left >= 0 ? arr[left] > arr[minIndex] : true;
		boolean rightBigger = right < arr.length ? arr[right] > arr[minIndex] : true;
		return leftBigger && rightBigger;
	}
	
	// 测试用例自己尝试写一下
	// 先要构造存在局部最小值的随机数组

	// 二分不是一定需要有序，只要确定某一端肯定有符合要求的结果，那么就不需要有序
}
