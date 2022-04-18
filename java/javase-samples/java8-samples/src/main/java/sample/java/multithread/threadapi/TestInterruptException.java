package sample.java.multithread.threadapi;

/**
 * 中断异常
 * https://cloud.tencent.com/developer/article/1592942
 * 
 * 
 * 
 * 什么时候会抛InterruptedException异常
 * 当阻塞方法收到中断请求的时候就会抛出InterruptedException异常
 * 
 */
public class TestInterruptException {

	
	public static void main(String[] args) {
		
		
		try {
			Thread.currentThread().wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
