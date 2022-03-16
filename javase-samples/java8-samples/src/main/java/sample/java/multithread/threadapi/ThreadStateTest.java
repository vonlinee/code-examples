package sample.java.multithread.threadapi;

public class ThreadStateTest {
	public static void main(String[] args) throws InterruptedException {
		
		Thread thread = Thread.currentThread();
		
		//This method is designed for use in monitoring of the system state,
	    //not for synchronization control.
		
		System.out.println(thread.getState());  //RUNNABLE

		thread.interrupt();
		
		while (thread.isInterrupted()) { //Thread.interrupted();
			System.out.println(thread.getState() + "   " + Thread.currentThread().getState());
		}
		
		
	}
}
