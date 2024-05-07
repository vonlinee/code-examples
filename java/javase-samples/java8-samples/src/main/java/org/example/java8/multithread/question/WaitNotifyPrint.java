package org.example.java8.multithread.question;

/*
 * 简单复习：
 * 1.wait和notify都是Object类的方法。
 * 2.wait和notify必须要在synchronized代码块中执行，否则会抛异常。
 */
public class WaitNotifyPrint {

    private static int count = 0;
    //两个线程竞争该对象锁
    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            while (count <= 100) {
                //1. 拿到锁直接就打印。
                //2. 打印完，唤醒其他线程，自己就休眠。
                synchronized (lock) {
                    //拿到锁就打印
                    System.out.println(Thread.currentThread().getName() + ":" + count++);
                    //打印完，唤醒其他线程
                    lock.notify();
                    //如果任务还没结束，就调用wait()让出当前的锁
                    if (count <= 100) {
                        try {
                            //自己休眠
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        new Thread(task, "偶数").start();
        new Thread(task, "奇数").start();
    }
}