package code.example.java.multithread.threadapi;

public class ThreadYieldTest {
	public static void main(String[] args) {
		new RunnableThread().start();
		Thread.currentThread().setPriority(10);
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						
					}
				}
			}).start();
		}
		boolean holdsLock = Thread.holdsLock(new Object());
		if (holdsLock) {
			System.out.println("====================");
		}
	}
}

class RunnableThread extends Thread {

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (Thread.currentThread() == this) {
				Thread.yield();
			}
			System.out.println("==========");
		}
	}
}
