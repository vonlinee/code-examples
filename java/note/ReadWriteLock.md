





https://www.jianshu.com/p/9cd5212c8841



Synchronized存在明显的一个性能问题就是读与读之间互斥，简言之就是，我们编程想要实现的最好效果是，可以做到读和读互不影响，读和写互斥，写和写互斥，提高读写的效率，如何实现呢？



Java并发包中ReadWriteLock是一个接口，主要有两个方法，如下：

```java
public interface ReadWriteLock {
    /**
     * Returns the lock used for reading.
     * @return the lock used for reading
     */
    Lock readLock();

    /**
     * Returns the lock used for writing.
     * @return the lock used for writing
     */
    Lock writeLock();
}
```

ReadWriteLock管理一组锁，一个是只读的锁，一个是写锁。
Java并发库中ReetrantReadWriteLock实现了ReadWriteLock接口并添加了可重入的特性。
在具体讲解ReetrantReadWriteLock的使用方法前，我们有必要先对其几个特性进行一些深入学习了解。











