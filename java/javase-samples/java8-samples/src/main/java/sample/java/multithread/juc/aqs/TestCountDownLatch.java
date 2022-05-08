package sample.java.multithread.juc.aqs;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLatch {

    private static final CountDownLatch latch = new CountDownLatch(10);

    public static void main(String[] args) {
        latch.countDown();


        long count = latch.getCount();



        
    }

}
