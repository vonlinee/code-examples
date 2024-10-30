package org.example.workx;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class DelayedTask implements Delayed {
    private final String name;
    private final long delayTime;
    private final long startTime;

    public DelayedTask(String name, long delayTime) {
        this.name = name;
        this.delayTime = delayTime;
        this.startTime = System.currentTimeMillis() + delayTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        return Long.compare(this.getDelay(TimeUnit.MILLISECONDS), o.getDelay(TimeUnit.MILLISECONDS));
    }

    @Override
    public String toString() {
        return "Task: " + name + ", Delay: " + delayTime + "ms";
    }
}

public class DelayQueueExample {
    public static void main(String[] args) {
        DelayQueue<DelayedTask> delayQueue = new DelayQueue<>();
        // 添加任务到队列
        delayQueue.add(new DelayedTask("Task 1", 3000)); // 3秒后
        delayQueue.add(new DelayedTask("Task 2", 1000)); // 1秒后
        delayQueue.add(new DelayedTask("Task 3", 2000)); // 2秒后
        // 处理任务
        try {
            while (!delayQueue.isEmpty()) {
                DelayedTask task = delayQueue.take(); // 阻塞直到有元素
                System.out.println("Processing " + task);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}