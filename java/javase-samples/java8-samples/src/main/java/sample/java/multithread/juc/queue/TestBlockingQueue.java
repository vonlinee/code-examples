package sample.java.multithread.juc.queue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * https://blog.csdn.net/paincupid/article/details/47626819
 * 
 * https://www.baiyp.ren/CLH%E9%98%9F%E5%88%97%E9%94%81.html
 */
public class TestBlockingQueue {

    public static void main(String[] args) {

    	ArrayBlockingQueue<String> bq = new ArrayBlockingQueue<>(10);
    	bq.add("A");
    	bq.add("B");
    	bq.clear();
    	
    	
    	
    	
    	
    }
}
