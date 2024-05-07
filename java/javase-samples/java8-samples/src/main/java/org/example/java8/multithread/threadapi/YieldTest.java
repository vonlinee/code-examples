package org.example.java8.multithread.threadapi;

/**
 * yield的本质是把当前线程重新置入抢CPU时间的"队列"(队列只是说所有线程都在一个起跑线上.并非真正意义上的队列)
 * @author someone
 */
public class YieldTest extends Thread {

	public YieldTest(String name) {
		super(name);
	}

	@Override
	public void run() {
		for (int i = 1; i <= 50; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("" + this.getName() + "-----" + i);
			//当i为30时，该线程就会给调度器一个暗示：自己会让出CPU时间，让其他或者自己的线程执行（也就是谁先抢到谁执行）
			//当前Thread线程不一定让出线程
			if (i == 30) {
				Thread.yield();
			}
		}
	}

	public static void main(String[] args) {
		YieldTest yt1 = new YieldTest("Thread-1");
		yt1.setPriority(6);
		YieldTest yt2 = new YieldTest("Thread-2");
		YieldTest yt3 = new YieldTest("Thread-3");
		YieldTest yt4 = new YieldTest("Thread-4");
		YieldTest yt5 = new YieldTest("Thread-5");
		yt1.start();
		yt2.start();
		yt3.start();
		yt4.start();
		yt5.start();
	}
}
