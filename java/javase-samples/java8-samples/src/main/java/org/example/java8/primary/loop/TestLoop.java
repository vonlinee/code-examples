package org.example.java8.primary.loop;

public class TestLoop {
	
	public static void main(String[] args) {

	}
	
	public static void test1() {
		for (int i = 0; ; i++) {
			if (i == 20) break;
		}
		
		int i = 0;
		while (true) {
			i++;
			if (i == 20) break;
		}
	}
}
