package org.lancoo.crm.utils;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolExample {

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        
        // 创建 ForkJoinPool
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        // 创建 ForkJoinTask
        SumTask task = new SumTask(numbers, 0, numbers.length);
        
        // 计算结果
        int result = forkJoinPool.invoke(task);

        forkJoinPool.getPoolSize();

        System.out.println("Sum: " + result);

        System.out.println(forkJoinPool);
    }
}

// 自定义 RecursiveTask
class SumTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2; // 任务分割的阈值
    private final int[] numbers;
    private final int start;
    private final int end;

    public SumTask(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) {
            // 如果任务小于阈值，则直接计算
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += numbers[i];
            }
            return sum;
        } else {
            // 任务分割
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(numbers, start, mid);
            SumTask rightTask = new SumTask(numbers, mid, end);
            
            // 并行执行子任务
            leftTask.fork(); // 异步执行左边的子任务
            int rightResult = rightTask.compute(); // 同步执行右边的子任务
            int leftResult = leftTask.join(); // 获取左边子任务的结果
            
            return leftResult + rightResult; // 合并结果
        }
    }
}