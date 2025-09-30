package org.lancoo.crm.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExecutorExample {

    public static void main(String[] args) {
        // 创建一个 ScheduledThreadPoolExecutor，线程池大小为5
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);

        // 提交一个延迟任务
        scheduler.schedule(() -> {
            System.out.println("Task executed after a delay of 3 seconds: " + System.currentTimeMillis());
        }, 3, TimeUnit.SECONDS);

        // 提交一个固定频率的任务
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("Task executed at a fixed rate of 2 seconds: " + System.currentTimeMillis());
        }, 0, 2, TimeUnit.SECONDS);

        // 提交一个固定延迟的任务
        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("Task executed with a fixed delay of 2 seconds: " + System.currentTimeMillis());
            try {
                // 模拟任务处理时间
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 0, 2, TimeUnit.SECONDS);

        // 运行一段时间后关闭调度器
        try {
            Thread.sleep(10000); // 主线程休眠10秒，观察调度效果
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            scheduler.shutdown(); // 关闭调度器
        }
    }
}