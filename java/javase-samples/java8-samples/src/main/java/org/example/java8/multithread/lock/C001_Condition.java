package org.example.java8.multithread.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @since created on 2022年6月22日
 */
public class C001_Condition {

	public static void main(String[] args) throws InterruptedException {
		ItemQueue itemQueue = new ItemQueue(10);
		// Create a producer and a consumer.
		Thread producer = new Producer(itemQueue);
		Thread consumer = new Consumer(itemQueue);
		// Start both threads.
		producer.start();
		consumer.start();
		// Wait for both threads to terminate.
		producer.join();
		consumer.join();
	}

	// 同步容器
	static class ItemQueue {
		private Object[] items = null;
		private int current = 0;
		private int placeIndex = 0;
		private int removeIndex = 0;

		private final Lock lock;
		private final Condition isEmpty;
		private final Condition isFull;

		public ItemQueue(int capacity) {
			this.items = new Object[capacity];
			lock = new ReentrantLock();
			isEmpty = lock.newCondition();
			isFull = lock.newCondition();
		}

		public void add(Object item) throws InterruptedException {
			lock.lock();
			// 消费完，等待生产者生产
			while (current >= items.length)
				isFull.await();
			items[placeIndex] = item;
			placeIndex = (placeIndex + 1) % items.length;
			++current;
			// Notify the consumer that there is data available.
			isEmpty.signal();
			lock.unlock();
		}

		public Object remove() throws InterruptedException {
			Object item = null;
			lock.lock();
			while (current <= 0) {
				isEmpty.await();
			}
			item = items[removeIndex];
			removeIndex = (removeIndex + 1) % items.length;
			--current;
			// Notify the producer that there is space available.
			isFull.signal();
			lock.unlock();
			return item;
		}

		public boolean isEmpty() {
			return (items.length == 0);
		}
	}

	static class Producer extends Thread {
		private final ItemQueue queue;

		public Producer(ItemQueue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			String[] numbers = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" };
			try {
				for (String number : numbers) {
					System.out.println("[Producer]: " + number);
				}
				queue.add(null);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	static class Consumer extends Thread {
		private final ItemQueue queue;

		public Consumer(ItemQueue queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			try {
				do {
					Object number = queue.remove();
					System.out.println("[Consumer]: " + number);
					if (number == null) {
						return;
					}
				} while (!queue.isEmpty());
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}
}
