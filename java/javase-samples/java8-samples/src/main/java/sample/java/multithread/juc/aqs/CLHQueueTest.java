package sample.java.multithread.juc.aqs;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CLH队列：
 */
public class CLHQueueTest {
	public static void main(String[] args) {
		final KFC kfc = new KFC();
		Executor executor = Executors.newFixedThreadPool(5);
		for (int i = 1; i <= 35; i++) {
			executor.execute(kfc::takeout);
		}
	}
}

class KFC {
	private final Lock lock = new CLHLock();
	private int i = 0; // 共享资源

	public void takeout() {
		try {
			lock.lock();
			System.out.println(Thread.currentThread().getName() + ": Take the " + (++i) + " Selling");
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
}

class CLHLock implements Lock {
	// Tail is one of all threads. After all threads come in, set themselves as tail
	private final AtomicReference<QNode> tail;
	// Precircuit node, each thread is unique.（密封节点，线程唯一）
	private final ThreadLocal<QNode> myPred;
	// The current node indicates yourself, and each thread is unique.
	private final ThreadLocal<QNode> myNode;

	public CLHLock() {
		this.tail = new AtomicReference<>(new QNode());
		this.myNode = ThreadLocal.withInitial(QNode::new);
		this.myPred = new ThreadLocal<>();
	}

	@Override
	public void lock() {
		// 当前线程的节点
		QNode node = myNode.get();
		// 拿到锁
		node.locked = true;
		// Place yourself in the queue's tail and return the previous value. The first
		// time I will get the new qNode in the constructor.
		QNode pred = tail.getAndSet(node);
		// put the old node into the front drive node.
		myPred.set(pred);
		// This is a spin-waiting process in the Locked domain waiting for the
		// predecessor node.
		while (pred.locked) {
			// 自旋
		}
		// Print information for MYNODE, MYPRED
		peekNodeInfo();
	}

	private void peekNodeInfo() {
		System.out.println("Lock Status => " + myNode.get().locked);
	}

	@Override
	public void unlock() {
		// unlock. Get your own Node. Set your Locked to false.
		QNode node = myNode.get();
		node.locked = false;
		myNode.set(myPred.get());
	}
}

// CLH Lock是自旋锁
class QNode {
	volatile boolean locked;
}

interface Lock {
	void lock();
	void unlock();
}