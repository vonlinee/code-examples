package code.example.pattern.nullobject;

public class EmptyBox<T> extends Box<T> {
    protected EmptyBox(T some) {
        super(some);
    }

    @Override
    public void doSomething() {
        System.out.println("do nothing");
    }
}
