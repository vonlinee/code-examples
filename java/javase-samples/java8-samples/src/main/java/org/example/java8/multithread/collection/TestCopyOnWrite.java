package org.example.java8.multithread.collection;

/**
 * @since created on 2022年8月1日
 */
public class TestCopyOnWrite {

    public static void main(String[] args) {
        //CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        //list.add("1");
        //list.add("2");

        Object lock = new Object();
        for (int i = 0; i < 10; i++) {
            synchronized (lock) {
                System.out.println(lock);
            }
        }
    }
}
