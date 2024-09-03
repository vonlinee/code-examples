package org.example.java8.multithread.threadapi;

import org.junit.jupiter.api.Test;

/**
 * 中断异常
 * https://cloud.tencent.com/developer/article/1592942
 * <p>
 * <p>
 * <p>
 * 什么时候会抛InterruptedException异常
 * 当阻塞方法收到中断请求的时候就会抛出InterruptedException异常
 */
public class TestInterruptException {


    public static void main(String[] args) {


        try {
            Thread.currentThread().wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test() throws InterruptedException {
        Object obj = new Object();
        obj.wait();
    }

}
