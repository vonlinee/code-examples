package org.example.java8.multithread.juc.aqs;

import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {

	
	public static void main(String[] args) {
//		LockSupport.park();
		
		LockSupport.unpark(Thread.currentThread());



		
	}
}
