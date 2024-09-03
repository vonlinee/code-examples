package org.cloud.crm.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExample {
    public static void main(String[] args) throws InterruptedException {
        // 创建一个大小为 5 的线程池
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        // 延迟 1 秒后执行任务
        executor.schedule(new Task(), 1, TimeUnit.SECONDS);

        // 延迟 2 秒后开始执行任务，然后每隔 3 秒重复执行任务
        executor.scheduleAtFixedRate(new Task2(), 2, 2, TimeUnit.SECONDS);

        // 延迟 2 秒后开始执行任务，然后在上一次任务执行完成后，等待 3 秒再执行下一次任务
        executor.scheduleWithFixedDelay(new Task3(), 3, 3, TimeUnit.SECONDS);
        Thread.sleep(100 * 1000);
        // 关闭线程池
        executor.shutdown();
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println("延迟一秒执行 " + System.currentTimeMillis());
        }
    }

    static class Task2 implements Runnable {
        @Override
        public void run() {
            System.out.println("延迟 2 秒后开始执行任务，然后每隔 3 秒重复执行任务 " + System.currentTimeMillis());
        }
    }

    static class Task3 implements Runnable {
        @Override
        public void run() {
            System.out.println("延迟 3 秒后开始执行任务，然后在上一次任务执行完成后，等待 3 秒再执行下一次任务 " + System.currentTimeMillis());
        }
    }
}
