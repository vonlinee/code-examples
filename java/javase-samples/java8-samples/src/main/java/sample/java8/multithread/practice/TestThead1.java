package sample.java8.multithread.practice;

public class TestThead1 {

	// 对象锁
	private static Object obj = new Object();
	private static volatile int num = 10;

	public static void main(String[] args) {
		new Thread(new Runnable() { // 匿名内部类
			@Override
			public void run() {
				synchronized (obj) {
					while (num > 0) {
						if (num % 2 == 0) {
							try {
								obj.wait(); // 释放锁进入等待队列（等待池），线程2获取到对象锁
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						num--;
						if (num >= 0) {
							System.out.println("B");
						}
						obj.notify(); // 唤醒等待队列中线程2进入锁池竞争对象锁
					}
				}
			}
		}, "thread1").start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (obj) {
					while (num > 0) {
						if (num % 2 != 0) {
							try {
								obj.wait(); // 释放锁进入等待队列（等待池），线程1获取到对象锁
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						num--;
						if (num >= 0) {
							System.out.println("A");
						}
						obj.notify(); // 唤醒等待队列中线程1进入锁池竞争对象锁
					}
				}
			}
		}, "thread2").start();
	}
}
