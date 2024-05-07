



```java
public static CompletableFuture<Void> runAsync(Runnable runnable) {
    return asyncRunStage(asyncPool, runnable);
}
```

private static final boolean useCommonPool = (ForkJoinPool.getCommonPoolParallelism() > 1);

private static final Executor asyncPool = useCommonPool ?
        ForkJoinPool.commonPool() : new ThreadPerTaskExecutor();




/**
 * The argument supplied to the current call to
 * java.util.concurrent.locks.LockSupport.park.
 * Set by (private) java.util.concurrent.locks.LockSupport.setBlocker
 * Accessed using java.util.concurrent.locks.LockSupport.getBlocker
 */
volatile Object parkBlocker;



