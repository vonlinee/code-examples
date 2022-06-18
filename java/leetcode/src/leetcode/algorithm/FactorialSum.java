package leetcode.algorithm;

/**
 * 求阶乘之和
 * @author vonline
 *
 */
public class FactorialSum {
	
	public static void main(String[] args) {
		fun(10);
		fun1(10);
	}
	
	public static void fun(int N) {
		long r = 0;
		for (int i = 1; i <= N; i++) {
			r = r + factorial(i);
		}
		System.out.println(r);
	}
	
	/**
	 * 求N的阶乘
	 * @param N
	 * @return
	 */
	public static long factorial(int N) {
		long r = 1;
		for (int i = 1; i <= N; i++) {
			r *= i; 
		}
		return r;
	}
	
	/**
	 * 求N的阶乘之和：0! + 1! + 2! + ... + N!
	 * @param N
	 */
	public static void fun1(int N) {
		long ans = 0;
		long cur = 1;
		for (int i = 1; i <= N; i++) {
			// 缓存上一个数的阶乘，同时相加
			cur = cur * i; 
			ans += cur;
		}
		System.out.println(ans);
	}
}
