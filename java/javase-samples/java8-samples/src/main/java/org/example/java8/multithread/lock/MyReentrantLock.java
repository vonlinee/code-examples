package org.example.java8.multithread.lock;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractOwnableSynchronizer;
import java.util.concurrent.locks.Condition;

abstract class AQS extends AbstractOwnableSynchronizer {

    protected boolean isHeldExclusively() {
        throw new UnsupportedOperationException();
    }

    public class ConditionObject implements Condition {
        @Override
        public final void signal() {
            if (!isHeldExclusively())
                throw new IllegalMonitorStateException();
        }

        @Override
        public void await() throws InterruptedException {

        }

        @Override
        public void awaitUninterruptibly() {

        }

        @Override
        public long awaitNanos(long nanosTimeout) throws InterruptedException {
            return 0;
        }

        @Override
        public boolean await(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public boolean awaitUntil(Date deadline) throws InterruptedException {
            return false;
        }

        @Override
        public void signalAll() {

        }
    }
}

public class MyReentrantLock {

    Sync sync = new Sync();

    static class Sync extends AQS {

        @Override
        protected final boolean isHeldExclusively() {
            // While we must in general read state before owner,
            // we don't need to do so to check if current thread is owner
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }
    }

    public static void main(String[] args) {
        AQS.ConditionObject condition = new MyReentrantLock().sync.newCondition();

        condition.signal();
    }
}
