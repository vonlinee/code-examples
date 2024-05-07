package org.example.java8.multithread.lock;


import org.example.java8.multithread.utils.Utils;

public class WaitNotifyTest3 {
	public static void main(String args[]) {
		
		Object lock = new Object();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronized (lock) {
						System.out.println("wait");
						lock.wait();
						System.out.println("==============");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				Utils.sleepSeconds(5);
				System.out.println("==============");
				lock.notify();
			}
		}).start();
	}
}

//每个对象都是一把锁
//wait和notify要执行，所在线程必须拿到该对象的monitor
//拿到该对象的monitor有3种方法：
//1.调用该对象的synchronized实例方法
//2.执行同步代码块synchronized
// synchronized (lock) {  尝试拿该对象的monitor
//
// }
//3.对于Class类型的实例, 调用该类的synchronized静态方法（Class实例没有synchronized实例方法）
//每个Class类的实例都是单例的
