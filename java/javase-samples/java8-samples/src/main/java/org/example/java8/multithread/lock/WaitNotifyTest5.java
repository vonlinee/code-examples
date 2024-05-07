package org.example.java8.multithread.lock;

public class WaitNotifyTest5 {
	public static void main(String args[]) throws InterruptedException {
		
		Object lock = new Object();
		lock.notify(); //java.lang.IllegalMonitorStateException
		
		lock.wait();  //java.lang.IllegalMonitorStateException
		
	}
}

//0 new #3 <java/lang/Object>
//3 dup
//4 invokespecial #8 <java/lang/Object.<init> : ()V>
//7 astore_1
//8 aload_1
//9 invokevirtual #19 <java/lang/Object.notify : ()V>
//12 aload_1
//13 invokevirtual #22 <java/lang/Object.wait : ()V>
//16 return
