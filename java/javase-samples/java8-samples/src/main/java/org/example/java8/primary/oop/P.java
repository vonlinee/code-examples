package org.example.java8.primary.oop;

public class P {

    static {
        System.out.println("class p init1");
    }

    {
        System.out.println("object p init");
    }

    static {
        System.out.println("class p init2");
    }

    public P() {
        System.out.println("constructor in P class");
    }

}

class C extends P {

    static {
        System.out.println("class C init1");
    }

    {
        System.out.println("object C init");
    }

    static {
        System.out.println("class C init2");
    }

    public C() {
        System.out.println("constructor in C class");
    }
}

class TestClass {
    public static void main(String[] args) {
        C c = new C();
    }
}