package org.example.java8.multithread.concurrency;

public class TwoSums {

	private int sum1 = 0, sum2 = 0;

	public void add(int val1, int val2) {
		synchronized (this) {
			this.sum1 += val1;
			this.sum2 += val2;
		}
	}
	
	
	public static void main(String[] args) {
		
		TwoSums s1 = new TwoSums();
		TwoSum1 s2 = new TwoSum1();
		
		StringBuilder sb = new StringBuilder();
		final int times = 10000;  // 10 个线程
		long start = System.currentTimeMillis();
		for(int i = 0; i < times; i++) {
			new Thread(() ->  {
				s1.add(1, 1);
			}).start();
		}
		sb.append(System.currentTimeMillis() - start).append(" ms\n");
		start = System.currentTimeMillis();
		for(int i = 0; i < times; i++) {
			new Thread(() ->  {
				s2.add(1, 1);
			}).start();
		}
		sb.append(System.currentTimeMillis() - start).append(" ms\n");
		System.out.println(sb.toString() + " " + s1.sum1 + " " + s1.sum2);
	}
}

class TwoSum1 {

	private int sum1 = 0;
	private int sum2 = 0;

	private Integer sum1Lock = new Integer(1);
	private Integer sum2Lock = new Integer(2);

	public void add(int val1, int val2) {
		synchronized (this.sum1Lock) {
			this.sum1 += val1;
		}
		synchronized (this.sum2Lock) {
			this.sum2 += val2;
		}
	}
}