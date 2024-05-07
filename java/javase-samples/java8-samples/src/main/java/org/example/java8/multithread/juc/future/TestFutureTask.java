package org.example.java8.multithread.juc.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.junit.jupiter.api.Test;

public class TestFutureTask {

    private ThreadFactory namedThreadFactory = new DefaultThreadFactory("thread-start-runner-%d");
    private ExecutorService service = new ThreadPoolExecutor(10, 20, 800, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    public static void main(String[] args) throws Exception {
        task1();
    }

    @Test
    public static void task1() throws InterruptedException, ExecutionException {
        Callable<Integer> call = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("正在计算结果...");
                Thread.sleep(3000);
                return 1;
            }
        };
        FutureTask<Integer> task = new FutureTask<>(call);

        Thread thread = new Thread(task);
        thread.start();
        // do something
        System.out.println(" 干点别的...");
        Integer result = task.get();  //主线程堵塞，直到FutureTask线程拿到结果
        System.out.println("拿到的结果为：" + result);
    }
}
