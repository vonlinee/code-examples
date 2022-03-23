package sample.java.multithread.threadapi;

/**
 * https://www.yisu.com/zixun/209635.html
 * @author someone
 */
public class ThreadInterruptTest {
	// 这里用来打印消耗的时间
	private static long time = 0;

	private static void printContent(String content) {
		System.out.println(content + "  时间：" + (System.currentTimeMillis() - time));
	}

	public static void main(String[] args) {
		test1();
	}

	private static void test1() {
		Thread1 thread1 = new Thread1();
		thread1.start();
		// 延时3秒后interrupt中断
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		thread1.interrupt();
		printContent("执行中断");
	}

	private static class Thread1 extends Thread {
		@Override
		public void run() {
			time = System.currentTimeMillis();
			int num = 0;
			while (true) {
				if (isInterrupted()) {
					printContent("当前线程 isInterrupted");
					break;
				}
				num++;
				if (num % 100 == 0) {
					printContent("num : " + num);
				}
			}
		}
	}
}