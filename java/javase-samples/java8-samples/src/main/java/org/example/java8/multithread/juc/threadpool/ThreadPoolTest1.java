package org.example.java8.multithread.juc.threadpool;

import java.util.concurrent.*;

public class ThreadPoolTest1 {

    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, 5,
                0L, TimeUnit.SECONDS, queue);

        boolean result = executor.prestartCoreThread();

        executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println("rejected" + r);
            }
        });

        Thread.yield();
        Object obj = new Object();

        executor.submit(() -> {

        });
        executor.shutdown();
        executor.shutdownNow();

    }
}
