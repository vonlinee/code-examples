package sample.java.multithread.atomic;

import java.util.concurrent.atomic.AtomicBoolean;

public class TestAtomicBoolean {

	static AtomicBoolean flag = new AtomicBoolean();
	
	public static void main(String[] args) {
		// 不安全
		flag.set(false);
		
		boolean oldVal = flag.compareAndSet(false, true);
		
		flag.lazySet(true);
		
		boolean andSet = flag.getAndSet(true);
		
		flag.weakCompareAndSet(true, false);
		
		boolean b = flag.get();
	}
}
