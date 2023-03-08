package sample.java8.multithread.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * https://www.jianshu.com/p/d7a9d3bcb21d
 */
public class RetrantLockTest {
	
	private Lock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		Object blocker = LockSupport.getBlocker(Thread.currentThread());
		LockSupport.parkUntil(3L);
		
	}
}
