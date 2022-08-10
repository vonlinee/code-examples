package sample.java.multithread.primary;
/**
 * 
 * @since created on 2022年8月1日
 */
public class TestThreadSleep {

	public static void main(String[] args) {
		Object lock = new Object();
		Thread t1 = new Thread(() -> {
			String threadName = Thread.currentThread().getName();
			synchronized (lock) {
				System.out.println(threadName + "获取到锁");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, "t1");
		t1.start();
		for (int i = 0; i < 5; i++) {
			new Thread(() -> {
				String threadName = Thread.currentThread().getName();
				synchronized (lock) {
					System.out.println(threadName + "获取到锁");
				}
			}).start();;
		}
	}
	
	
	
}