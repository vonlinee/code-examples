package sample.java.multithread.concurrency.reorder;

import java.util.concurrent.TimeUnit;

/**
 * https://jenkov.com/tutorials/java-concurrency/java-happens-before-guarantee.html
 */
public class FrameExchanger {

	private long framesStoredCount = 0;
	private long framesTakenCount = 0;

	private boolean hasNewFrame = false;

	private Object frame = null;

	// called by Object producing thread
	public void storeFrame(Object frame) {
		this.frame = frame;
		this.framesStoredCount++;
		this.hasNewFrame = true;
		System.out.println(Thread.currentThread().getName() + " -> " + frame);
	}

	// called by Object drawing thread
	public Object takeFrame() {
		while (!hasNewFrame) {
			// busy wait until new frame arrives
		}
		Object newObject = this.frame;
		this.framesTakenCount++;
		this.hasNewFrame = false;
		System.out.println(Thread.currentThread().getName() + " <- " + frame);
		return newObject;
	}
	
	public static void main(String[] args) {
		FrameExchanger exchanger = new FrameExchanger();
		
		Thread frameProducingThread = new Thread(new FrameOperationRunnable(exchanger) {
			@Override
			public void run() {
				exchanger.storeFrame(new Object());
			}
		}, "frameProducingThread");
		
		
		Thread frameDrawingThread = new Thread(new FrameOperationRunnable(exchanger) {
			@Override
			public void run() {
				exchanger.takeFrame();
			}
		}, "frameDrawingThread");
		
		frameProducingThread.start();
		frameDrawingThread.start();
		
		try {
			TimeUnit.SECONDS.sleep(3);
			System.out.println(exchanger.framesStoredCount);
			System.out.println(exchanger.framesTakenCount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

abstract class FrameOperationRunnable implements Runnable {
	
	FrameExchanger exchanger;
	
	public FrameOperationRunnable(FrameExchanger exchanger) {
		this.exchanger = exchanger;
	}
}
