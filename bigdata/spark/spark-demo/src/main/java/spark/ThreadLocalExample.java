package spark;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadLocalExample {

  // 创建一个 ThreadLocal 变量
  private static final ThreadLocal<Integer> threadLocalValue = ThreadLocal.withInitial(() -> 0);

  public static void main(String[] args) {
    ThreadLocal<Integer> threadLocalValue = new ThreadLocal<>();
    ThreadPoolExecutor
      executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
    for (int i = 0; i < 3; i++) {
      final int threadId = i;
      executor.execute(() -> {
        // 设置线程局部变量
        threadLocalValue.set(threadId);
        System.out.println("Thread " + Thread.currentThread().getName() + " has value: " + threadLocalValue.get());
        threadLocalValue.remove();
      });
    }
    executor.shutdown();
  }
}
