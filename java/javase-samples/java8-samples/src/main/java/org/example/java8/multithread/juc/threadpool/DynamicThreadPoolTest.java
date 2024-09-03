package org.example.java8.multithread.juc.threadpool;

//import cn.hippo4j.core.executor.DynamicThreadPoolExecutor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class DynamicThreadPoolTest {

    @Test
    public void test() {
//        DynamicThreadPoolExecutor executor = new DynamicThreadPoolExecutor(
//                5, 100, 3000,
//                TimeUnit.SECONDS, 5_000, true, 2, new ArrayBlockingQueue<>(1024)
//                , "threadPoolId", new ThreadFactory() {
//            @Override
//            public Thread newThread(Runnable r) {
//                return new Thread(r);
//            }
//        }, new RejectedExecutionHandler() {
//            @Override
//            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//
//            }
//        });
//
//        executor.submit(() -> {
//            System.out.println("111");
//        });
    }
}
