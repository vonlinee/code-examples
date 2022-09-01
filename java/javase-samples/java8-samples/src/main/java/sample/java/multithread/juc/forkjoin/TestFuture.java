package sample.java.multithread.juc.forkjoin;

import org.junit.jupiter.api.Test;
import sample.java.multithread.utils.Utils;

import java.util.concurrent.*;
import java.util.function.BiFunction;

public class TestFuture {

    public static void main(String[] args) {
        CompletableFuture<Integer> future = new CompletableFuture<>();

    }

    private int num;

    @Test
    public void test1() throws ExecutionException, InterruptedException {

        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return num + 10;
            }
        });

        new Thread(task, "t1").start();
        new Thread(task, "t2").start();
        new Thread(task, "t3").start();

        Future<Void> future;

        // task.cancel(true);
        System.out.println("task is running");
        Integer integer = task.get();

        System.out.println("result = " + integer);
    }

    @Test
    public void test2() {

    }
}
