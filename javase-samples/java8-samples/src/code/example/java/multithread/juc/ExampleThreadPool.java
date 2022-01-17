package code.example.java.multithread.juc;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ExampleThreadPool {
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
//		Executor
//		ThreadPoolExecutor
//		ExecutorService
		
		int corePoolSize = 10;
        int maximumPoolSize = 10;
        long keepAliveTime = 1000;
        TimeUnit unit = TimeUnit.SECONDS;
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<Runnable>();
		ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		AtomicInteger i = new AtomicInteger(0);
		pool.submit(() -> {
			while (true) {
				int _i = i.getAndIncrement();
				System.out.println(_i);
			}
		});
		pool.shutdown();
		if (!pool.isShutdown()) {
			
		}
	}
}
