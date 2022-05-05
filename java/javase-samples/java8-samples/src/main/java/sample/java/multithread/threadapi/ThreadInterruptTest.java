package sample.java.multithread.threadapi;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * https://www.yisu.com/zixun/209635.html
 * 
 * JSR-51
 * public void interrupt();
 * 
 * Thread中存在一个字段
 * private volatile Interruptible blocker; // sun.nio.ch.Interruptible接口
 * JDK注释：The object in which this thread is blocked in an interruptible I/O operation, if any.
 * The blocker's interrupt method should be invoked after setting this thread's interrupt status.
 * 
 * public interface Interruptible {
 * 		void interrupt(Thread var1);
 * }
 * 
 * Unless the current thread is interrupting itself, which is always permitted, the Thread.checkAccess() 
 * method of this thread is invoked, which may cause a SecurityException to be thrown.
 * 如果是当前线程中断它自己，这种是被允许的，否则调用Thread.checkAccess()方法来检查访问权限
 * 
 * If this thread is blocked in an invocation of the Object#wait(), Object#wait(long), 
 * or Object.wait(long, int) methods of the Object class,
 * or of the join(), join(long)}, join(long, int), sleep(long), or sleep(long, int) 
 * methods of this class, then its interrupt status will be cleared and it will receive an 
 * InterruptedException.
 * 1.如果该线程由于调用了Object#wait()方法或者调用了该Thread的join，sleep方法，那么该线程的中断状态将会被清除，并且接收到InterruptedException
 * 
 * If this thread is blocked in an I/O operation upon an java.nio.channels.InterruptibleChannel, then the channel 
 * will be closed, the thread's interrupt status will be set, and the thread will receive a java.nio.channels.ClosedByInterruptException
 * 2.如果该线程被阻塞在java.nio.channels.InterruptibleChannel的IO操作中，那么调用Thread.interrupt方法将会关闭该InterruptibleChannel，
 * 然后设置该线程的中断状态，并且该线程将会接收到一个java.nio.channels.ClosedByInterruptException
 * 
 * If this thread is blocked in a java.nio.channels.Selector, then the thread's interrupt status will be set 
 * and it will return immediately from the selection operation, possibly with a non-zero value, 
 * just as if the selector's java.nio.channels.Selector#wakeup method were invoked.
 * 3.如果该线程阻塞在java.nio.channels.Selector，那么此线程的中断状态将会被设置，并且Selector的selection操作将会立刻返回结果，但可能为非0值
 * （即使Selector上发生的事件数为0），就如同调用了java.nio.channels.Selector的wakeup方法
 * 
 * If none of the previous conditions hold then this thread's interrupt status will be set.
 * 如果前面的条件都不满足，则该线程的中断状态将会被设置
 * Interrupting a thread that is not alive need not have any effect
 * 
 * Thread.interrupt并非中断当前线程，而是中断该Thread实例线程，即设置中断状态
 * 
 */
public class ThreadInterruptTest {
	// 这里用来打印消耗的时间
	private static long time = 0;

	private static void printContent(String content) {
		System.out.println(content + "  时间：" + (System.currentTimeMillis() - time));
	}

	public static void main(String[] args) throws IOException {
		test1();
		
		Selector selector = Selector.open();
		
	}

	private static void test1() {
		Thread1 thread1 = new Thread1();
		thread1.start();
		// 延时3秒后interrupt中断
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		// 在main线程中中断其他线程
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