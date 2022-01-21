package code.example.java.multithread.lock;

public class WaitNotifyTest4 {
	public static void main(String args[]) {
		
		Object lock = new Object();
		
		synchronized (lock) {
			//System.out.println("============");
		}
	}
}

//0 new #3 <java/lang/Object>
//3 dup
//4 invokespecial #8 <java/lang/Object.<init> : ()V>
//7 astore_1
//8 aload_1
//9 dup
//10 astore_2
//11 monitorenter
//12 aload_1
//13 invokevirtual #16 <java/lang/Object.notify : ()V>
//16 aload_2
//17 monitorexit
//18 goto 24 (+6)
//21 aload_2
//22 monitorexit
//23 athrow
//24 return
