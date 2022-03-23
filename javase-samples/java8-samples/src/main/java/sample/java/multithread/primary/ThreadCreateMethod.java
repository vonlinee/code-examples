package sample.java.multithread.primary;

public class ThreadCreateMethod {

    public static void main(String[] args) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("111");
            }
        };
        System.out.println(thread);
    }
}
