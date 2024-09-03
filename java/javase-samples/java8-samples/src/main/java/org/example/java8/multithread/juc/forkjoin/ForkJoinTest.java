package org.example.java8.multithread.juc.forkjoin;

import java.util.Random;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinTest {

    public static void main(String[] args) {
        long[] array = new long[2000];

        long expectedSum = 0;
        for (int i = 0; i < array.length; i++) {
            array[i] = randomInt();
            expectedSum += array[i];
        }

        System.out.println(expectedSum);

        ForkJoinTask<Long> fjtask;
    }

    static Random random = new Random(0);

    public static long randomInt() {
        return random.nextInt(10000);
    }
}

/**
 * 求和任务
 */
class SumTask extends RecursiveTask<Long> {

    static final int THRESHOLD = 500; //阈值
    long[] array;
    int start;
    int end;

    public SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start < THRESHOLD) {
            long sum = 0;
            for (int i = 0; i < end; i++) {
                sum += this.array[i];
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return sum;
        } else { //任务太大进行拆分
            int middle = (end - start) / 2;
            System.out.println(middle);
        }
        return null;
    }
}

class Fibonacci extends RecursiveTask<Integer> {
    final int n;

    public Fibonacci(int n) {
        this.n = n;
    }

    @Override
    public Integer compute() {
        if (n <= 1) return n;
        Fibonacci f1 = new Fibonacci(n - 1);
        f1.fork();
        Fibonacci f2 = new Fibonacci(n - 2);
        return f2.compute() + f1.join();
    }
}