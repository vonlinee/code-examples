package org.example.java8.multithread.juc.forkjoin;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskExample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long starttime = System.currentTimeMillis();
        //input2生成， 需要耗费3秒
        FutureTask<Integer> futureTask1 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(3000);
                return 5;
            }
        });
        new Thread(futureTask1).start();
        //input1生成，需要耗费2秒
        FutureTask<Integer> futureTask2 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(2000);
                return 3;
            }
        });
        new Thread(futureTask2).start();
        Integer integer1 = futureTask1.get();
        Integer integer2 = futureTask2.get();
        System.out.println(algorithm(integer1, integer2));
        long endtime = System.currentTimeMillis();
        System.out.println("用时" + (endtime - starttime));
    }

    //这是我们要执行的算法
    public static int algorithm(int input, int input2) {
        return input + input2;
    }
}