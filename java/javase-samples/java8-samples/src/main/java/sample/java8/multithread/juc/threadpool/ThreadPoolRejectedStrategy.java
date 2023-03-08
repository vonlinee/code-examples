package sample.java8.multithread.juc.threadpool;

import java.util.concurrent.*;

/**
 * 线程池拒绝策略
 * <p>
 * 当线程池的任务缓存队列已满并且线程池中的线程数目达到maximumPoolSize时，如果还有任务到来就会采取任务拒绝策略，通常有以下四种策略：
 * 1.ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。
 * 2.ThreadPoolExecutor.DiscardPolicy：丢弃任务，但是不抛出异常。
 * 3.ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新提交被拒绝的任务
 * 4.ThreadPoolExecutor.CallerRunsPolicy：由调用线程（提交任务的线程）处理该任务
 * <p>
 * 线程池的默认拒绝策略为AbortPolicy，即丢弃任务并抛出RejectedExecutionException异常
 * <p>
 * 使用场景：
 * 1.AbortPolicy：在任务不能再提交的时候，抛出异常，及时反馈程序运行状态。如果是比较关键的业务，推荐使用此拒绝策略，这样子在系统不能承载更大的并发量的时候，能够及时的通过异常发现。
 * 2.DiscardPolicy：使用此策略，可能会使我们无法发现系统的异常状态。建议是一些无关紧要的业务采用此策略。例如，本人的博客网站统计阅读量就是采用的这种拒绝策略。
 * 3.DiscardOldestPolicy：此拒绝策略，是一种喜新厌旧的拒绝策略。是否要采用此种拒绝策略，还得根据实际业务是否允许丢弃老任务来认真衡量
 * 4.CallerRunsPolicy：如果任务被拒绝了，则由调用线程（提交任务的线程）直接执行此任务
 */
public class ThreadPoolRejectedStrategy {

    public static void main(String[] args) {
        test3();
    }

    /**
     * 未设置拒绝策略
     * 报错
     * Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task java.util.concurrent.FutureTask@421faab1 rejected from java.util.concurrent.ThreadPoolExecutor@2b71fc7e[Running, pool size = 5, active threads = 5, queued tasks = 100, completed tasks = 0]
     * 且报错信息是在某些线程还未执行时就打印在控制台的
     */
    public static void test1() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);
        ThreadFactory factory = r -> new Thread(r, "test-thread-pool");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.SECONDS, queue, factory);
        while (true) {
            executor.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " -> " + queue.size());
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 如果我们想要根据实际业务场景需要，设置其他的线程池拒绝策略，可以通过ThreadPoolExecutor重载的构造方法进行设置
     * <p>
     * 通过spring提供的org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor构建线程池
     */
    public static void test2() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(100);
        ThreadFactory factory = r -> new Thread(r, "test-thread-pool");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.SECONDS, queue, factory);

        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

        while (true) {
            executor.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " -> " + queue.size());
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * 测试CallerRunsPolicy
     *
     * 下面是打印结果
     * test-thread-pool:执行任务
     * test-thread-pool:执行任务
     * test-thread-pool:执行任务
     * test-thread-pool:执行任务
     * main:执行任务
     * test-thread-pool:执行任务
     * test-thread-pool:执行任务
     * test-thread-pool:执行任务
     */
    public static void test3() {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(10);
        ThreadFactory factory = r -> new Thread(r, "test-thread-pool");
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 5,
                0L, TimeUnit.SECONDS, queue, factory, new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + ":执行任务");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
