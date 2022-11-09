package sample.java.multithread.jmm;

public class JavaMemoryModel {

    class A {
        int val;
        public void set() {
            this.val++;
        }
    }

    public static void main(String[] args) {
        int i = 10;
        new Thread(() -> {
            System.out.println(i);
            // Variable used in lambda expression should be final or effectively final
            // i++; 不能对i进行操作
        }).start();
    }
}
