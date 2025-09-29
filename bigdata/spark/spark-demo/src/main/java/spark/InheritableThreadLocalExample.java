package spark;

public class InheritableThreadLocalExample {
  private static final InheritableThreadLocal<String> inheritableThreadLocal =
    new InheritableThreadLocal<String>() {
      @Override
      protected String initialValue() {
        return "Initial Value";
      }
    };

  public static void main(String[] args) {
    // 设置父线程的值
    inheritableThreadLocal.set("Parent Value");

    // 创建子线程
    Thread childThread = new Thread(() -> {
      // 获取子线程的值
      String value = inheritableThreadLocal.get();
      System.out.println("Child Thread Value: " + value); // 输出 "Parent Value"
    });

    childThread.start();

    try {
      childThread.join(); // 等待子线程完成
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // 主线程的值
    System.out.println("Main Thread Value: " + inheritableThreadLocal.get()); // 输出 "Parent Value"
  }
}