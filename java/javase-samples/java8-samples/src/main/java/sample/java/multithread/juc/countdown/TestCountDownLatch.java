package sample.java.multithread.juc.countdown;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 同步计数器，当计数器数值减为0时，所有受其影响而等待的线程将会被激活，这样保证模拟并发请求的真实性
 * <p>
 * CountDownLatch是一个同步工具类，用来协调多个线程之间的同步，或者说起到线程之间的通信（而不是用作互斥的作用）。
 * CountDownLatch能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。
 * 使用一个计数器进行实现。计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。
 * 当计数器的值为0时，表示所有的线程都已经完成一些任务，然后在CountDownLatch上等待的线程就可以恢复执行接下来的任务。
 * <p>
 * CountDownLatch典型用法： 1、某一线程在开始运行前等待n个线程执行完毕。将CountDownLatch的计数器初始化为new
 * CountDownLatch(n)， 每当一个任务线程执行完毕，就将计数器减1
 * countdownLatch.countDown()，当计数器的值变为0时，在CountDownLatch上await()的线程就会被唤醒。
 * 一个典型应用场景就是启动一个服务时，主线程需要等待多个组件加载完毕，之后再继续执行。
 * <p>
 * CountDownLatch典型用法： 2、实现多个线程开始执行任务的最大并行性。注意是并行性，不是并发，强调的是多个线程在某一时刻同时开始执行。
 * 类似于赛跑，将多个线程放到起点，等待发令枪响，然后同时开跑。做法是初始化一个共享的CountDownLatch(1)，将其计算器初始化为1，
 * 多个线程在开始执行任务前首先countdownlatch.await()，当主线程调用countDown()时，计数器变为0，多个线程同时被唤醒。
 * <p>
 * CountDownLatch是一次性的，计算器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，当CountDownLatch使用完毕后，它不能再次被使用。
 */
public class TestCountDownLatch {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        // 计数器为3
        CountDownLatch latch = new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            Runnable runnable = () -> {
                try {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("子线程" + threadName + "开始执行");
                    Thread.sleep((long) (Math.random() * 10000));
                    System.out.println("子线程" + threadName + "执行完成");
                    latch.countDown();// 当前线程调用此方法，则计数减一
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            service.execute(runnable);
        }
        String threadName = Thread.currentThread().getName();
        try {
            System.out.println("主线程" + threadName + "等待子线程执行完成...");
            latch.await();// 阻塞当前线程，直到计数器的值为0
            System.out.println("主线程" + threadName + "开始执行...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<Runnable> shutdownNow = service.shutdownNow();
        if (shutdownNow.size() > 0) {
            System.out.println(shutdownNow);
        }
    }
}
