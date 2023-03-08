package sample.java8.multithread.concurrency.keyword;

public class TestVolatileAtomic {
	
	static volatile int i;
	
	public static void main(String[] args) throws InterruptedException {
		
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				System.out.println(i++);
			}
		});
		
		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 10000; i++) {
				System.out.println(i--);
			}
		});
		
		t1.start();
		t2.start();
		
		Thread.sleep(3000);
		System.out.println(i);
	}
}
