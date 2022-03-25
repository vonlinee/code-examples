package sample.java.multithread.juc.future;

import java.util.concurrent.CompletableFuture;

/**
 * CompletableFuture<T> implements Future<T>, CompletionStage<T>
 */
public class TestCompletableFuture {
	
	public static void main(String[] args) {
		
	}
	
	public static void test1() {
		CompletableFuture.runAsync(() -> {
			
		});
	}
}
