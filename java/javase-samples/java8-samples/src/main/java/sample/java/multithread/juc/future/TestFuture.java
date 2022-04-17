package sample.java.multithread.juc.future;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class TestFuture {

	
	public static void main(String[] args) {
		
		Future<Integer> future = null;
		
        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(this + " is running in " + Thread.currentThread().getName());
                return 5;
            }
        });
        new Thread(futureTask).start();;
	}
	
	public static void test1() {
		
	}
 }
