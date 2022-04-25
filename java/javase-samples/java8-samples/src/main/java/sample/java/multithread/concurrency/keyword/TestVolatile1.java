package sample.java.multithread.concurrency.keyword;

public class TestVolatile1 {

    static int a = 0;
    static boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            if (flag) {         //3
                System.out.println(a * a); //4
            }
        }).start();
        a = 2;              //1
        flag = true;        //2
    }
}
