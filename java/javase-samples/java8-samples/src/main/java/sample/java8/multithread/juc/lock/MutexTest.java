package sample.java8.multithread.juc.lock;

import java.util.concurrent.Semaphore;

public class MutexTest {
	
	//com.sun.corba.se.impl.orbutil.concurrent.Mutex
	Mutex mutex1 = new Mutex();
	
	Semaphore semaphoreMutex = new Semaphore(1); //Mutex
	Semaphore fairSemaphore = new Semaphore(1, true); //fair
	Semaphore unfairSemaphore = new Semaphore(1, false); //unfair
	
	public void lock() {
	}
}
