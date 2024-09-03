package org.example.java8.multithread.lock;

public class WaitNotifyTest2 {

	static final Object lock = new Object();

	public static void main(String[] args) {
		
		synchronized (lock) {
            try {
				System.out.println("wait");
                lock.wait();
            } catch (InterruptedException e) {
				e.printStackTrace();
            }
        }
	}
}

//0 new #3 <java/lang/Object>
//3 dup
//4 invokespecial #8 <java/lang/Object.<init> : ()V>
//7 astore_1
//8 aload_1
//9 dup
//10 monitorenter
//11 monitorexit
//12 return
