package org.example.java8.multithread.juc.future;

import io.netty.util.concurrent.DefaultThreadFactory;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class TestFutureTask {

    private ThreadFactory namedThreadFactory = new DefaultThreadFactory("thread-start-runner-%d");
    private ExecutorService service = new ThreadPoolExecutor(10, 20, 800, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

    @Test
    public void task1() throws InterruptedException, ExecutionException {
        FutureTask<Integer> task = new FutureTask<>(() -> {
            System.out.println("正在计算结果...");
            Thread.sleep(3000);
            return 1;
        });

        Thread thread = new Thread(task);
        thread.start();
        // do something
        System.out.println(" 干点别的...");
        Integer result = task.get();  //主线程堵塞，直到FutureTask线程拿到结果
        System.out.println("拿到的结果为：" + result);
    }
}
