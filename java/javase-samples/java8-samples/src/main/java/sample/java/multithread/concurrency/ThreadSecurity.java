package sample.java.multithread.concurrency;

import sample.java.utils.Utils;

/**
 * 线程安全问题与JMM
 * @author Administrator
 */
public class ThreadSecurity {
	
	private int i = 0;
	
	public void r() {
		while (true) {
			int local1 = i; // 读
			System.out.println(local1);
			Utils.sleep(3);
			
			int local2 = i; // 第二次读
			System.out.println(local2);
			if (local1 != local2) {
				System.out.println("0");
			}
		}
	}
	
	public void w() {
		while (true) {
			i++;
		}
	}
	
	public static void main(String[] args) {
		ThreadSecurity test = new ThreadSecurity();
		new Thread(() -> test.w()).start();
		new Thread(() -> test.r()).start();
	}
}
