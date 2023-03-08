package sample.java8.multithread.juc.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

/**
 * CompletableFuture<T> implements Future<T>, CompletionStage<T>
 * 
 * 完成阶段
 * java.util.concurrent.CompletionStage<T>
 * 
 * 
 */
public class TestCompletableFuture {
	
	public static void main(String[] args) {
		test1();
	}
	
	/**
	 * 异步异常：https://blog.csdn.net/china_eboy/article/details/115460605
	 */
	public static void test1() {
		// CompletableFuture会把异常吞掉
		CompletableFuture.runAsync(() -> {
			mayThrowExceptionMethod(5, 5);
		});
	}
	
	public static void test2() {
		int commonPoolParallelism = ForkJoinPool.getCommonPoolParallelism();
		System.out.println(commonPoolParallelism);
	}
	
	public static int mayThrowExceptionMethod(int i, int j) {
		System.out.println("mayThrowExceptionMethod");
		return i / (i - j);
	}
}
