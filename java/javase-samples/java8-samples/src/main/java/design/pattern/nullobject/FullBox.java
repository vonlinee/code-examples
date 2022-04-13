package design.pattern.nullobject;

public class FullBox<T> extends Box<T> {
    protected FullBox(T some) {
        super(some);
    }

    @Override
    public void doSomething() {
        System.out.println("do something");
    }
}
