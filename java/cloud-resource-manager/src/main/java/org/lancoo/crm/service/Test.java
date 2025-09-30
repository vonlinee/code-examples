package org.lancoo.crm.service;

import org.lancoo.crm.utils.DateTimeUtils;

import java.util.concurrent.*;

public class Test {


    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " " + DateTimeUtils.nowTime());
        }
    }

    public static void main(String[] args) {

        DataPullScheduledTaskExecutor executor = new DataPullScheduledTaskExecutor(20);

        for (int i = 0; i < 1000; i++) {
            executor.scheduleAtFixedRate(new Task(), 0, 2, TimeUnit.SECONDS);
        }

        executor.prestartCoreThread();

        executor.prestartAllCoreThreads();

        ThreadPoolExecutor executor1 = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        executor1.setCorePoolSize(50);

        executor1.submit(new Runnable() {
            @Override
            public void run() {

            }
        });

        Future<Object> submit = executor1.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });

        Future<Object> submit1 = executor1.submit(new Runnable() {
            @Override
            public void run() {

            }
        }, new Object());

        executor1.shutdown();

        while (true) {

        }
    }
}
