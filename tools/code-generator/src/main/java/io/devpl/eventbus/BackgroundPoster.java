package io.devpl.eventbus;

/**
 * Posts events in background.
 * @author Markus
 */
final class BackgroundPoster implements Runnable, Poster {

    private final PendingPostQueue queue;
    private final DefaultEventBus eventBus;

    private volatile boolean executorRunning;

    BackgroundPoster(DefaultEventBus eventBus) {
        this.eventBus = eventBus;
        queue = new PendingPostQueue();
    }

    public void enqueue(Subscription subscription, Object event) {
        PendingPost pendingPost = PendingPost.obtainPendingPost(subscription, event);
        synchronized (this) {
            queue.enqueue(pendingPost);
            if (!executorRunning) {
                executorRunning = true;
                eventBus.getExecutorService().execute(this);
            }
        }
    }

    @Override
    public void run() {
        try {
            try {
                while (true) {
                    PendingPost pendingPost = queue.poll(1000);
                    if (pendingPost == null) {
                        synchronized (this) {
                            // Check again, this time in synchronized
                            pendingPost = queue.poll();
                            if (pendingPost == null) {
                                executorRunning = false;
                                return;
                            }
                        }
                    }
                    // TODO
                    eventBus.invokeSubscriber(pendingPost);
                }
            } catch (InterruptedException e) {

            }
        } finally {
            executorRunning = false;
        }
    }
}
