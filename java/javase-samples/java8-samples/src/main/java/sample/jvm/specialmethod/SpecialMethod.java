package sample.jvm.specialmethod;

public class SpecialMethod {


}

class A {
    private A() {
    }

    public A(int i) {
    }
}

class B extends A {

    public B(int i) {
        super(i);
    }
}