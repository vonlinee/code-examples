package org.example.jvm.classloader;


public class Test {

    public static void main(String[] args) {

        A a = new A();
    }
}

class A {

    B b;

    public A() {
        this.b = new B();
    }
}

class B {

    static void init() {
        throw new RuntimeException();
    }

    static {
        init();
    }
}