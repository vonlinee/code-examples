package org.springboot.sample.config;

public class ShanhyA implements Shanhy {

    @Override
    public void display() {
        System.out.println("AAAAAAAAAAAA");
    }

//	public static void main(String[] args) {
//		System.out.println(add(1,0));
//	}

    public static int add(int i, int sum) {
        if (i > 100)
            return sum;
        sum += (i++);
        return add(i, sum);
    }
}