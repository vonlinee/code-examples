package sample.java8.multithread.juc.aqs;

import java.util.concurrent.CompletableFuture;

public class ExampleCompletableFuture {
	public static void main(String[] args) {
		CompletableFuture<Long> future = new CompletableFuture<>();
		CompletableFuture<Void> futureList = CompletableFuture.allOf(future);
		
		
		
		
		
	}
}
