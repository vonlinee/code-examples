package code.example.java.multithread.lock;

public class WaitNotifyTest2 {
	public static void main(String args[]) {
		
		Object lock = new Object();
		
		synchronized (lock) {
			
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
