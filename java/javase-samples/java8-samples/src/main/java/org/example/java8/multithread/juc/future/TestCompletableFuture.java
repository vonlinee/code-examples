package org.example.java8.multithread.juc.future;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;

/**
 * CompletableFuture<T> implements Future<T>, CompletionStage<T>
 * <p>
 * 完成阶段
 * java.util.concurrent.CompletionStage<T>
 */
public class TestCompletableFuture {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 异步异常：<a href="https://blog.csdn.net/china_eboy/article/details/115460605">...</a>
     */
    public static void test1() {
        // CompletableFuture会把异常吞掉
        CompletableFuture.runAsync(() -> {
            mayThrowExceptionMethod(5, 5);
        });

        CompletableFuture<Object> future = new CompletableFuture<>();


        future.isCompletedExceptionally();
    }

    @Test
    public void test2() {
        int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism();
        System.out.println(commonPoolParallelism);
    }

    public static int mayThrowExceptionMethod(int i, int j) {
        System.out.println("mayThrowExceptionMethod");
        return i / (i - j);
    }

    @Test
    public void test() {
        CompletableFuture.runAsync(() -> {
            System.out.println(111);
        }).thenAccept(new Consumer<Void>() {
            @Override
            public void accept(Void unused) {
                System.out.println("thenAccept");
            }
        }).thenAcceptAsync(new Consumer<Void>() {
            @Override
            public void accept(Void unused) {
                System.out.println("thenAcceptAsync");
            }
        });
    }
}
