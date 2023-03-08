package sample.java8.multithread.juc.aqs;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * https://www.jianshu.com/p/9cd5212c8841
 * <p>
 * CopyOnWriteArrayList
 */
public class TestReadWriteLock {

    private static final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static final CopyOnWriteArrayList<Integer> cowlist = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        Lock rlock = readWriteLock.readLock();
        Lock wlock = readWriteLock.writeLock();


    }
}
