package sample.java.multithread.concurrency;

public class Test1 {

    public static void main(String[] args) {
        B b = new B();
        b.doSomething();
    }
}

class A {
    public synchronized void doSomething() {
        System.out.println(this + " doSomething");
    }
}

class B extends A {
    @Override
    public synchronized void doSomething() {
        System.out.println(this + " doSomething");
        super.doSomething();
    }
}