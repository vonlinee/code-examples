package io.sample.obs;
/**
 * 
 * @since created on 2022年11月21日
 */
public class Main {
	public static void main(String[] args) {
	    int i = 10;
	    new Thread(() -> {
	        System.out.println(i);
	        // Local variable i defined in an enclosing scope must be final or effectively final
	        // i++; // 不能对i进行操作
	    }).start();
	}
}
