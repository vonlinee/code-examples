

https://tech.meituan.com/2019/12/05/aqs-theory-and-apply.html



# AQS重要方法与ReentrantLock的关联

加锁过程：

1. 通过ReentrantLock的加锁方法Lock进行加锁操作。
2. 会调用到内部类Sync的Lock方法，由于Sync#lock是抽象方法，根据ReentrantLock初始化选择的公平锁和非公平锁，执行相关内部类（FairSync、NonfairSync）的lock方法，本质上都会执行AQS的acquire方法（此方法在AQS中是final修饰的）。
3. AQS的acquire方法会执行tryAcquire方法，但是由于tryAcquire方法需要自定义同步器实现，因此执行了ReentrantLock中的tryAcquire方法，由于ReentrantLock是通过公平锁和非公平锁内部类实现的tryAcquire方法，因此会根据锁类型不同，执行不同的tryAcquire方法。
4. tryAcquire是获取锁逻辑所在的方法，获取失败后，会执行框架AQS的后续逻辑，跟ReentrantLock自定义同步器无关。



解锁：

1. 通过ReentrantLock的解锁方法unlock进行解锁。
2. unlock方法会调用内部类Sync的release方法，该方法继承于AQS。
3. release中会调用tryRelease方法，tryRelease需要自定义同步器实现，tryRelease只在ReentrantLock中的Sync实现，因此可以看出，释放锁的过程，并不区分是否为公平锁。
4. 释放成功后，所有处理由AQS框架完成，与自定义同步器无关。



# 节点状态


SIGNAL：值为-1，后继节点的线程处于等待状态，而当前节点的线程如果释放了同步状态或者被取消，那么就会通知后继节点，让后继节点的线程能够运行

CONDITION：值为-2，节点在等待队列中，节点线程等待在Condition上，不过当其他的线程对Condition调用了signal()方法后，该节点就会从等待队列转移到同步队列中，然后开始尝试对同步状态的获取

PROPAGATE：值为-3，表示下一次的共享式同步状态获取将会无条件的被传播下去

CANCELLED：值为1，由于超时或中断，该节点被取消。 节点进入该状态将不再变化。特别是具有取消节点的线程永远不会再次阻塞
INITIAL	值为0，初始状态





# 通过ReentrantLock理解AQS

ReentrantLock中公平锁和非公平锁在底层是相同的，这里以非公平锁为例进行分析



加锁方法

```java
final void lock() {
    // 先尝试获取锁
    if (compareAndSetState(0, 1))  // 获取成功
        setExclusiveOwnerThread(Thread.currentThread());
    else  // 获取失败
        acquire(1);
}
```

看下acquire方法，acquire方法是在AQS中的final方法

```java
//以独占模式获取，忽略中断。通过调用至少一次tryAcquire来实现，并在成功后返回。否则线程将排队，
//可能会反复阻塞和取消阻塞，调用tryAcquire直到成功。此方法可用于实现方法Lock#lock方法
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}
```

## tryAcquire获取锁

tryAcquire方法需要AQS子类实现，它只在Sync的非公平和公平锁实现类中有实现，在Sync中没有实现

可以看出，这里只是AQS的简单实现，具体获取锁的实现方法是由各自的公平锁和非公平锁单独实现的（以ReentrantLock为例）。如果该方法返回了True，则说明当前线程获取锁成功，就不用往后执行了；如果获取失败，就需要加入到等待队列中。下面会详细解释线程是何时以及怎样被加入进等待队列中的。



### 公平锁版本

ReentrantLock用state表示所有者线程已经重复获取该锁的次数

FairSync中的实现如下

```java
//tryAcquire的公平版本。除非有递归调用或者没有waiter，否则不要授予访问权限(protected)
protected final boolean tryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) { // 表明没有线程持有锁，初始状态
        // 同时还要判断等待队列中是否有即将出队的节点，没有才加锁，可以看到对于公平锁，队列中的线程的优先级比新线程高
        if (!hasQueuedPredecessors() && compareAndSetState(0, acquires)) { // acquires = 1
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    else if (current == getExclusiveOwnerThread()) { // 当前线程=占有锁的线程
        int nextc = c + acquires;
        if (nextc < 0)
            throw new Error("Maximum lock count exceeded");
        setState(nextc);
        return true;
    }
    return false;
}
```

hasQueuedPredecessors是公平锁加锁时判断等待队列中是否存在有效节点的方法，是AQS的public final方法。如果返回false，说明当前线程可以争取共享资源；如果返回True，说明队列中存在有效节点，由于公平锁的特点，当前线程必须加入到等待队列中。

```java
public final boolean hasQueuedPredecessors() {
    // 此方法的正确性依赖于：1.head提前于tail被初始化，2.如果当前线程是第一次入队，那么head.next是确定的
    // The correctness of this depends on head being initialized before tail and on head.next being 
    // accurate if the current thread is first in queue.
    Node t = tail; // Read fields in reverse initialization order 按相反的初始化顺序读取字段
    Node h = head;
    Node s;
    // 判断头结点的下一个节点
    return h != t && ((s = h.next) == null || s.thread != Thread.currentThread());
}
```

双向链表中，第一个节点为虚节点，其实并不存储任何信息，只是占位。真正的第一个有数据的节点，是在第二个节点开始的。

1. 当h != t（即当前队列的头节点不等于尾节点，队列中存在有效节点）时： 如果(s = h.next) == null，表示等待队列正在有线程进行初始化，但只是进行到了tail指向head，没有将head指向tail，此时队列中有元素，需要返回True
2. 当h != t，如果(s = h.next) != null，说明此时队列中至少有一个有效节点。如果此时s.thread == Thread.currentThread()，说明等待队列的第一个有效节点中的线程与当前线程相同，那么当前线程是可以获取资源的；如果s.thread != Thread.currentThread()，说明等待队列的第一个有效节点线程与当前线程不同，那么当前线程必须加入进等待队列，同时返回false。

队列线程初始化的代码

```java
private Node enq(final Node node) {
    // 自旋
    for (;;) {
        Node t = tail;
        if (t == null) { // Must initialize ， 这里用到了Double Check
            if (compareAndSetHead(new Node()))
                tail = head;
        } else { // 尾节点不为空
            node.prev = t;  								// 5
            if (compareAndSetTail(t, node)) {				// 6
                t.next = node;								// 7
                return t;
            }
        }
    }
}
```

节点入队不是原子操作，所以会出现短暂的head != tail，此时Tail指向最后一个节点，而且tail指向Head。如果head没有指向tail（可见5、6、7行），这种情况下也需要将相关线程加入队列中。所以这块代码是为了解决极端情况下的并发问题

### 非公平版本

在java.util.concurrent.locks.ReentrantLock.NonfairSync中定义

```java
protected final boolean tryAcquire(int acquires) {
    return nonfairTryAcquire(acquires);
}
```

实际上是调用父类java.util.concurrent.locks.ReentrantLock.Sync的方法，之所以这么做是因为ReentrantLock#tryLock()方法，无论是非公平锁，还是公平锁都需要调用此方法获取锁

```java
final boolean nonfairTryAcquire(int acquires) {
    final Thread current = Thread.currentThread();
    int c = getState();
    if (c == 0) {
        if (compareAndSetState(0, acquires)) {
            setExclusiveOwnerThread(current);
            return true;
        }
    }
    // 锁重入
    else if (current == getExclusiveOwnerThread()) {
        int nextc = c + acquires; // + 1
        if (nextc < 0) // overflow
            throw new Error("Maximum lock count exceeded"); // 超过最大的锁数量
        setState(nextc);
        return true;
    }
    return false;
}
```





## 加入等待队列

当执行acquire(1)时，会通过tryAcquire获取锁。在这种情况下，如果获取锁失败，就会调用addWaiter加入到等待队列中去

```java
// NofairSync
final void lock() {
    if (compareAndSetState(0, 1))
        setExclusiveOwnerThread(Thread.currentThread());
    else
        acquire(1);
}
// FairSync
final void lock() {
    acquire(1);
}
// AQS的final方法，NofairSync和FairSync都调用此方法
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}
```

AQS中的静态方法，中断当前线程

```java
static void selfInterrupt() {
    Thread.currentThread().interrupt();
}
```





### addWaiter

addWaiter是AQS中的private方法，同样不需要实现，这里注意一点，是在子类中通过继承自父类的方法acquire中调用父类的私有方法的

```java
private Node addWaiter(Node mode) {  
    // Node.EXCLUSIVE 独占模式，ReentrantLock是独占锁
    Node node = new Node(Thread.currentThread(), mode);
    // Try the fast path of enq; backup to full enq on failure
    Node pred = tail;
    // 队列不为空
    if (pred != null) {
        node.prev = pred; // 新节点的前驱节点指向原来的队尾节点，这里不需要CAS操作，因为队列本身实际上此时还没变化的
        if (compareAndSetTail(pred, node)) { // CAS将新节点设置为队列尾节点
            pred.next = node;
            return node;
        }
    }
    enq(node);
    return node;
}
```

对于一个节点来说

```java
class Node {
    Node prev;  // 共享数据
    Node next;  // 共享数据
}
```

node.prev = pred;只是修改了局部变量的属性，因此不涉及到线程共享

如果队列为空，调用AQS的另一个private方法enq入队

```java
private Node enq(final Node node) {
    // 自旋
    for (;;) {
        Node t = tail;
        if (t == null) { // Must initialize ， 这里用到了Double Check
            if (compareAndSetHead(new Node()))
                tail = head;
        } else { // 尾节点不为空
            node.prev = t;
            if (compareAndSetTail(t, node)) {
                t.next = node;
                return t;
            }
        }
    }
}
```

从这里来看addWaiter和enq的部分代码实际上是相同的效果





## 出队时机

### acquireQueued

上文解释了addWaiter方法，这个方法其实就是把对应的线程以Node的数据结构形式加入到双端队列里，返回的是一个包含该线程的Node。而这个Node会作为参数，进入到acquireQueued方法中。acquireQueued方法可以对排队中的线程进行“获锁”操作。

此方法处于AQS中

总的来说，一个线程获取锁失败了，被放入等待队列，acquireQueued会把放入队列中的线程不断去获取锁，直到获取成功或者不再需要获取，中断。

下面我们从“何时出队列？”和“如何出队列？”两个方向来分析一下acquireQueued源码：下面的方法对于公平锁和非公平锁都适用

```java
final boolean acquireQueued(final Node node, int arg) { // node是刚加入队列的节点
    // 标记是否成功拿到资源
    boolean failed = true;
    try {
        // 标记等待过程中是否中断过
        boolean interrupted = false;
        // 自旋，要么获取锁，要么中断
        for (;;) {
            // 获取当前节点的前驱节点
            final Node p = node.predecessor();
            // 如果p是头结点，说明当前节点在真实数据队列的首部，此时应该尝试获取锁（真实的头结点是虚节点）
            if (p == head && tryAcquire(arg)) {  // arg = 1
                setHead(node); // 获取锁成功，头指针移动到当前node
                p.next = null; // help GC
                failed = false;
                return interrupted;  //返回false，则不会调用selfInterrupt();
            }
            // 1.p为头节点且当前没有获取到锁（可能被新线程抢占了）
            // 2.p不为头结点，此时要判断当前节点是否应该被阻塞，防止无限循环浪费资源。
            if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                interrupted = true; //返回true，则会调用selfInterrupt阻塞线程
        }
    } finally {
        if (failed)
            cancelAcquire(node);
    }
}
```

当前节点被阻塞条件：前驱节点的waitStatus为-1

```java
public final void acquire(int arg) {
    if (!tryAcquire(arg) &&
        acquireQueued(addWaiter(Node.EXCLUSIVE), arg))
        selfInterrupt();
}
```



### shouldParkAfterFailedAcquire

AQS的静态方法，和名字一样，是否应该在获取锁失败后park线程

如果p为头节点，但是没获取到锁，那么pred（头节点）和node就是连着的

```java
// p是node的前驱节点, node是当前节点
private static boolean shouldParkAfterFailedAcquire(Node pred, Node node) { 
    // pred可能是头节点，也可能不是
    int ws = pred.waitStatus;
    if (ws == Node.SIGNAL)  // 唤醒状态 -1
        /*
         * This node has already set status asking a release to signal it, so it can safely park.
         */
        return true;
    if (ws > 0) {  // 通过枚举值知道waitStatus>0是取消状态
        /*
         * 如果前驱节点被取消，那么跳过前驱节点
         * Predecessor was cancelled. Skip over predecessors and indicate retry.
         */
        do {
            node.prev = pred = pred.prev; // 循环向前查找取消节点，把取消节点从等待队列中剔除
        } while (pred.waitStatus > 0);
        pred.next = node;
    } else {
        /*
         * waitStatus must be 0 or PROPAGATE.  Indicate that we need a signal, but don't park yet.  
         * Caller will need to retry to make sure it cannot acquire before parking.
         */
        compareAndSetWaitStatus(pred, ws, Node.SIGNAL); // 设置当前节点的前驱节点等待状态为SIGNAL
    }
    return false;
}
```



### parkAndCheckInterrupt

AQS中的final方法，问题，这里的this指向的是谁？

```java
// parkAndCheckInterrupt主要用于挂起当前线程，阻塞调用栈，返回当前线程的中断状态。
private final boolean parkAndCheckInterrupt() {
    LockSupport.park(this); // 挂起此线程
    return Thread.interrupted();
}

// LockSupport方法
public static void park(Object blocker) {
    Thread t = Thread.currentThread();
    setBlocker(t, blocker);
    UNSAFE.park(false, 0L);
    setBlocker(t, null);
}

private static void setBlocker(Thread t, Object arg) {
    // Even though volatile, hotspot doesn't need a write barrier here.
    UNSAFE.putObject(t, parkBlockerOffset, arg);
}
```



## CANCELLED状态节点生成

acquireQueued方法中的Finally代码，公平和非公平都会调用

```java
final boolean acquireQueued(final Node node, int arg) {
    boolean failed = true;
    try {
        boolean interrupted = false;
        // 自旋获取锁
        for (;;) {
            final Node p = node.predecessor();
            if (p == head && tryAcquire(arg)) {
                setHead(node);
                p.next = null;    // help GC
                failed = false;
                return interrupted;
            }
            // 是否中断此线程
            if (shouldParkAfterFailedAcquire(p, node) && parkAndCheckInterrupt())
                interrupted = true;
        }
    } finally {
        if (failed)  // 出队的节点获取锁失败
            cancelAcquire(node);
    }
}
```

通过cancelAcquire方法，将Node的状态标记为CANCELLED

```java
private void cancelAcquire(Node node) {
    // Ignore if node doesn't exist
    if (node == null)
        return;
    node.thread = null;

    // Skip cancelled predecessors
    Node pred = node.prev;
    while (pred.waitStatus > 0)
        node.prev = pred = pred.prev;

    // predNext is the apparent node to unsplice. CASes below will
    // fail if not, in which case, we lost race vs another cancel
    // or signal, so no further action is necessary.
    Node predNext = pred.next;

    // Can use unconditional write instead of CAS here.
    // After this atomic step, other Nodes can skip past us.
    // Before, we are free of interference from other threads.
    node.waitStatus = Node.CANCELLED;

    // If we are the tail, remove ourselves.
    if (node == tail && compareAndSetTail(node, pred)) {
        compareAndSetNext(pred, predNext, null);
    } else {
        // If successor needs signal, try to set pred's next-link
        // so it will get one. Otherwise wake it up to propagate.
        int ws;
        if (pred != head &&
            ((ws = pred.waitStatus) == Node.SIGNAL ||
             (ws <= 0 && compareAndSetWaitStatus(pred, ws, Node.SIGNAL))) &&
            pred.thread != null) {
            Node next = node.next;
            if (next != null && next.waitStatus <= 0)
                compareAndSetNext(pred, predNext, next);
        } else {
            unparkSuccessor(node);
        }
        node.next = node; // help GC
    }
}
```













