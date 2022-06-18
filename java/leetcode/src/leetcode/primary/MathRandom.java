package leetcode.primary;

/**
 * https://www.bilibili.com/video/BV1Zr4y1W7ww?p=13
 * 
 * 随机数生成
 */
public class MathRandom {

	public static void main(String[] args) {
		test1();
	}
	
	// 不同语言有不同随机函数
	public static void test1() {
		double random = Math.random(); // 范围[0, 1)，左闭右开，而且是等概率返回一个值
		
		// 计算机里的数是有精度的，因此计算机里的数不是无穷的，而是离散的有限值
		double expectedValue = 0.3d;
		
		int testTimes = 1000000;
		int count = 0;
		for (int i = 0; i < testTimes; i++) {
			if (Math.random() < expectedValue) {
				count ++;
			}
		}
		
		// 计算概率
		System.out.println((double) count / (double) testTimes);
		
		System.out.println("===================");
		
		// 在[0, N)上仍然等概率返回
		count = 0;
		expectedValue = 4; 
		double N = 8;
		for (int i = 0; i < testTimes; i++) {
			if (Math.random() * N < expectedValue) {
				count ++;
			}
		}
		// 概率为 expectedValue / N
		System.out.println((double) count / (double) testTimes);// 小于4的概率接近50%
		
		System.out.println("===================");
		
		
		// 结论：Math.random()返回值在[0,x)的概率为x
		
		N = 9;
		
		int counts[] = new int[9];
		
		for (int i = 0; i < testTimes; i++) {
			int answer = (int) (Math.random() * N);
			counts[answer]++;
		}
		// 每种数出现的概率
		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + "这个数出现了" + counts[i] + "次！");
		}
		
		
		System.out.println("=========================");
		count = 0;
		double x = 0.17;
		for (int i = 0; i < testTimes; i++) {
			if (xToXPower2() < x) {
				count ++;
			}
		}
		System.out.println((double) count / (double) testTimes);// 小于4的概率接近50%
		System.out.println(Math.pow((double) x, 2));// 小于4的概率接近50%
		
		System.out.println((double) 1 - Math.pow((double) 1 - x, 2));
	}
	
	/**
	 * 返回[0, 1)的一个小数
	 * 任意的x，x属于[0, 1), [0,x)上的数出现概率由原来的x调整为x的平方
	 * TODO 分析用Math.min的概率 = 1-(1-x)^2
	 * @return
	 */
	public static double xToXPower2() {
		// 两次随机行为：只有两次都在[0,x)范围，最终结果才会在[0,x)范围
		// 由概率论可知：最终的概率为第一个数在[0,x)范围的概率（x）乘以第二个数在[0,x)范围的概率（x）= x平方
		return Math.max(Math.random(), Math.random());
	}
	
	// 概率论求分布函数
	
	/**
	 * 返回[0, 1)的一个小数
	 * 任意的x，x属于[0, 1), [0,x)上的数出现概率由原来的x调整为x的平方
	 * TODO 分析用Math.min的概率 = 1-(1-x)^2
	 * @return
	 */
	public static double xToXPower3() {
		// 两次随机行为：只有两次都在[0,x)范围，最终结果才会在[0,x)范围
		// 由概率论可知：最终的概率为第一个数在[0,x)范围的概率（x）乘以第二个数在[0,x)范围的概率（x）= x平方
		return Math.min(Math.random(), Math.random());
	}
}
