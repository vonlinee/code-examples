package org.example.java8.multithread.juc.threadpool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的submit吞异常
 * <p>
 * 为什么线程池的submit不抛出异常？
 * <a href="https://www.bilibili.com/video/BV1y44y1A7mr?spm_id_from=333.337.search-card.all.click">...</a>
 */
public class ThreadPoolTest3 {

    ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 10, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));

    @Test
    public void testSubmitAndExecute() {
        pool.submit(this::test); // 不会抛异常
        pool.execute(this::test); // 抛异常


        pool.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return null;
            }
        });
    }

    @Test
    public void test2() {

        System.out.println(Math.pow(2, 31) - 1);

        System.out.println(Math.pow(2, 31) - 1 > (double) Integer.MAX_VALUE);
        System.out.println(Math.pow(2, 29) - 1 > Integer.MAX_VALUE);

    }

    public void test() {
        int i = 1 / 0;
    }
}

// 线程池提交execute()与submit()方法有什么区别？

// 区别就是submit可以拿到futruetask，而submit也是调用的execute。这里有个坑就是task.get会阻塞执行这条语句的线程，
// 直到对应的任务执行结束拿到结果

