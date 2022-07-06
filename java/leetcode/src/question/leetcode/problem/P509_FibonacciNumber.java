package question.leetcode.problem;

/**
 * 斐波拉契问题
 * https://ssg.leetcode-cn.com/problems/fibonacci-number/  简单
 * 
 * 
 * 斐波那契数列（Fibonacci sequence），又称黄金分割数列、因数学家列昂纳多·斐波那契（Leonardoda Fibonacci）
 * 以兔子繁殖为例子而引入，故又称为“兔子数列”，指的是这样一个数列：1、1、2、3、5、8、13、21、34、……
 * 在数学上，斐波那契数列以如下被以递推的方法定义：F(1)=1，F(2)=1, F(n)=F(n - 1)+F(n - 2)（n ≥ 3，n ∈ N*）
 * 
 */
public class P509_FibonacciNumber {

	public static void main(String[] args) {
		System.out.println(fibonacci1(1));
		System.out.println(fibonacci1(2));
		System.out.println(fibonacci1(3));
		System.out.println(fibonacci1(4));
		System.out.println(fibonacci1(5));
	}

	public static int fibonacci1(int n) {
		int i, n1 = 1, n2 = 1, sum = 0;
		for (i = 3; i <= n; i++) {
			sum = n1 + n2;
			n1 = n2;
			n2 = sum;
		}
		return sum;
	}
}
