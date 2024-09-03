package org.example.jvm.gc.algorithm;

import java.util.concurrent.TimeUnit;

/**
 * -xms2m
 */
public class ObjectFinalization {

    private final byte[][] bytes = new byte[1024 * 1024 * 10][1024 * 1024 * 10];

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1000);
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println(this + "finalize");
    }
}
