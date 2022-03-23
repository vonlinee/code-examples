package sample.java.multithread.juc.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

//Executor
//ThreadPoolExecutor
//ExecutorService
public class ExampleThreadPool2 {
	
	public static void main(String[] args) {
		ExecutorService mCachedThreadPool = Executors.newCachedThreadPool();
        mCachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                int p = 0;
                while (p < 10) {
                    p++;
                    //把当前线程的名字用handler让textview显示出来
                    System.out.println(Thread.currentThread().getName() + " => " + p);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
	}
	
	
	public static void test2() {
		//nThreads => 最大线程数即maximumPoolSize
		//threadFactory => 创建线程的方法，用得少
		ExecutorService mFixedThreadPool= Executors.newFixedThreadPool(10);
        mFixedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
               //....逻辑代码自己控制
            }
        });
	}
}
