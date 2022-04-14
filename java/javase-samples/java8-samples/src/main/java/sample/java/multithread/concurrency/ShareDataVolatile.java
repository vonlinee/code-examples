package sample.java.multithread.concurrency;

public class ShareDataVolatile {

    public volatile static int count = 0;

    public static void main(String[] args) {
        final ShareDataVolatile data = new ShareDataVolatile();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int j = 0; j < 100; j++) {
                    data.addCount();
                }
                System.out.print(count + " ");
            }).start();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("\n count=" + count);
    }

    public void addCount() {
        // Non-atomic operation on volatile field 'count'
        count++;
    }
}