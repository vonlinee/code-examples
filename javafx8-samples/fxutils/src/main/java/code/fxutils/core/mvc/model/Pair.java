package code.fxutils.core.mvc.model;

import java.io.Serializable;

public class Pair<A, B> implements Serializable {

    private A a;
    private B b;

    private Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair<>(a, b);
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }
}
