package sample.java.multithread.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class RetrantLockTest {
	
	private Lock lock = new ReentrantLock();
	
	
	
	public static void main(String[] args) {
		Object blocker = LockSupport.getBlocker(Thread.currentThread());
		LockSupport.parkUntil(3L);
		
		
		
	}
}
