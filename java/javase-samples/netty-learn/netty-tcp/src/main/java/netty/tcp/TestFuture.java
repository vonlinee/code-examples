package netty.tcp;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                System.out.println(1);
            }
        });

        Void unused = future.get();
        future.cancel(true);
        System.out.println(unused);
    }
}
