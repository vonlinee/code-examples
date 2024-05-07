package org.example.java8.multithread.juc.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Executor(接口): 执行任务
 * 1.void execute(Runnable command); 在将来的某个时间执行给定的命令。根据Executor的实现，命令可能在新线程、线程池或调用中执行线程
 * 
 * ExecutorService(接口，extends Executor): 代表线程池的顶层接口
 * 1.void shutdown();
 * 2.<T> Future<T> submit(Callable<T> task);
 * 3.<T> Future<T> submit(Runnable task, T result);
 * 4.Future<?> submit(Runnable task);
 * 
 * ThreadPoolExecutor
 */
public class ThreadPoolExecutorTest {
	
	public static void main(String[] args) {
		
		int corePoolSize = 10;
        int maximumPoolSize = 10;
        long keepAliveTime = 1000;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
        
		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		AtomicInteger i = new AtomicInteger(0);
		for (int j = 0; j < 11; j++) {
			// 提交11个任务，但是只有10个线程，会出现复用的情况
			pool.submit(() -> {
				System.out.println(Thread.currentThread().getName());
			});
		}
		
		ThreadFactory threadFactory = pool.getThreadFactory();
		Thread thread = threadFactory.newThread(() -> {
			while (true) {
				System.out.println(" ad ");
			}
		});
		
		thread.start();
		
		pool.shutdown();
		if (pool.isShutdown()) {
			System.out.println("thread pool is shutdown!");
		}
	}
}
