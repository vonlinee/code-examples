package sample.java.multithread.juc.aqs;

import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {

	
	public static void main(String[] args) {
//		LockSupport.park();
		
		LockSupport.unpark(Thread.currentThread());



		
	}
}
