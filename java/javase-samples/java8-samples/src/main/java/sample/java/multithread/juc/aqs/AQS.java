package sample.java.multithread.juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * https://www.baiyp.ren/CLH%E9%98%9F%E5%88%97%E9%94%81.html
 */
public class AQS {
	public static void main(String[] args) {
		
		AbstractQueuedSynchronizer aqs;
		
		Lock lock = new ReentrantLock();

		Condition condition = lock.newCondition();



	}
}
