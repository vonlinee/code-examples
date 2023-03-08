package sample.java8.multithread.juc.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
	public static void main(String[] args) {
		test1();
	}

	public static void test1() {
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
		ThreadFactory factory = r -> new Thread(r, "test-thread-pool");
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS, queue, factory,
				new ThreadPoolExecutor.CallerRunsPolicy());
		for (int i = 0; i < 1000; i++) {
			executor.submit(() -> {
				try {
					System.out.println(Thread.currentThread().getName() + ":执行任务");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
	}
	
	public static void test2() {
		BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(5);
		ThreadFactory factory = r -> new Thread(r, "test-thread-pool");
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS, queue, factory);
		while (true) {
			executor.submit(() -> {
				try {
					System.out.println(Thread.currentThread().getName() + "  " + queue.size());
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		}
	}
}