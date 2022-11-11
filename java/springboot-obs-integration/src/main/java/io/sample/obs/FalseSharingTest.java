package io.sample.obs;

public class FalseSharingTest {

	public static void main(String[] args) {
		testPointer(new Pointer());
	}

	private static void testPointer(Pointer pointer) {
		long start = System.currentTimeMillis();
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 100000000; i++) { pointer.x++; }
		});
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 100000000; i++) { pointer.y++; }
		});

		t1.start();
		t2.start();
		try {
			t1.join();t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - start);
		System.out.println(pointer);
	}
}

class Pointer {
	volatile long x;
	volatile long y;
	// long p1, p2, p3, p4, p5, p6, p7;
}