package code.example.java.multithread.juc;

import java.util.concurrent.Semaphore;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

public class MutexTest {
	
	//com.sun.corba.se.impl.orbutil.concurrent.Mutex
	Mutex mutex1 = new Mutex();
	
	sun.awt.Mutex mutex2 = new sun.awt.Mutex();
	
	Semaphore semaphoreMutex = new Semaphore(1); //Mutex
	Semaphore fairSemaphore = new Semaphore(1, true); //fair
	Semaphore unfairSemaphore = new Semaphore(1, false); //unfair
	
}
