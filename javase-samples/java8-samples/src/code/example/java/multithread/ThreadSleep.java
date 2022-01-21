package code.example.java.multithread;

import java.util.concurrent.TimeUnit;

public class ThreadSleep {
	
	public static void seconds(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
