package sample.java.multithread.juc.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//Executor
//ThreadPoolExecutor
//ExecutorService
public class ExampleThreadPool {
	
	public static void main(String[] args) {
		
		int corePoolSize = 10;
        int maximumPoolSize = 10;
        long keepAliveTime = 1000;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
        
		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		AtomicInteger i = new AtomicInteger(0);
		for (int j = 0; j < 11; j++) {
			pool.submit(() -> {
				System.out.println(Thread.currentThread().getName());
			});
		}
		pool.shutdown();
		if (!pool.isShutdown()) {
			System.out.println("thread pool is shutdown!");
		}
	}
}
