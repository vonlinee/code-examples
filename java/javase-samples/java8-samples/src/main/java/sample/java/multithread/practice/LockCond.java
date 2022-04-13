package sample.java.multithread.practice;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockCond {

	private static volatile int count = 10;
	private static Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		Condition c1 = lock.newCondition();
		Condition c2 = lock.newCondition();
		new Thread(() -> {
			while (count > 0) {
				lock.lock();
				try {
					if (count % 2 == 0) {
						System.out.println("A");
						c1.await();
					}
					// 唤醒线程2
					c2.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					count--;
					lock.unlock();
				}
			}
		}).start();
		new Thread(() -> {
			while (count > 0) {
				lock.lock();
				try {
					if (count % 2 == 1) {
						System.out.println("B");
						c2.await();
					}
					// 唤醒线程1
					c1.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					count--;
					lock.unlock();
				}
			}
		}).start();
	}
}
