package io.devpl.eventbus.ext;

import java.util.concurrent.FutureTask;

public final class EventFuture<T> extends FutureTask<T> {

    public EventFuture(Runnable runnable, T result) {
        super(runnable, result);
    }
}
