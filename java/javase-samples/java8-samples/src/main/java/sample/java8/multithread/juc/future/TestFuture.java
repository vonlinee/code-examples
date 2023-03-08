package sample.java8.multithread.juc.future;

import sample.java8.multithread.utils.Utils;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html
 */
public class TestFuture {

	
	public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask<Integer> futureTask = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println(this + " is running in " + Thread.currentThread().getName());
                Utils.sleepSeconds(3);
                return 5;
            }
        });
        new Thread(futureTask).start();
        // 阻塞
        Integer integer = futureTask.get();

        System.out.println("res = " + integer);
    }
	
	public static void test1() {
		
	}
 }
