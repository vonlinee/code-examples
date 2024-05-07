package org.example.java8.multithread.concurrency;

/**
 * 共享数据
 */
public class C003_ShareDataVolatile {

	static volatile int count = 0;

	public static void main(String[] args) {
		final C003_ShareDataVolatile data = new C003_ShareDataVolatile();
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				for (int j = 0; j < 100; j++) {
					data.addCount();
				}
				System.out.print(count + " ");
			}).start();
		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.print("\n count=" + count);
	}

	public void addCount() {
		// Non-atomic operation on volatile field 'count'
		count++;
	}
}