package sample.java8.multithread.concurrency.keyword;

/**
 * flag变量是个标记，用来标识变量a是否已经被写入。这里假设有两个线程A和B，A首先执行write方法，
 * 随后B线程接着执行reader()方法。线程B在执行操作4的时候，能否看到线程A在操作1对共享变量的写入吗？
 */
public class TestReorder {
    static int a = 0;
    static boolean flag = false;

    public static void main(String[] args) {

        new Thread(() -> {
            a = 1;          // 1
            flag = true;    // 2
        }).start();

        new Thread(() -> {
            if (flag) {         // 3
                int i = a * a;  // 4
                System.out.println(i);
            }
        }).start();
    }
}
