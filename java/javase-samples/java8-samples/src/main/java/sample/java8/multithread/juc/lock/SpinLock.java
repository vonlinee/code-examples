package sample.java8.multithread.juc.lock;

public interface SpinLock {

	/**
	 * 加锁
	 */
	void lock();

	/**
	 * 解锁
	 */
	void unlock();
}