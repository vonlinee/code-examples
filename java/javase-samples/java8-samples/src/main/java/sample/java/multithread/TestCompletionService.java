package sample.java.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.RunnableFuture;

/**
 * @author vonline
 * @since 2022-08-23 12:59
 */
public class TestCompletionService {

    public static void main(String[] args) {
        CompletionService<Integer> service;
        RunnableFuture<Integer> rf = null;
        Callable<Integer> callable;
    }
}
