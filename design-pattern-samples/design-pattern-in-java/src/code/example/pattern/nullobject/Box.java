package code.example.pattern.nullobject;

public abstract class Box<T> {
    public abstract void doSomething();

    T something;

    protected Box(T some) {
        this.something = some;
    }

    public static <T> Box<T> wrap(T something) {
        if (something == null) {
            return new EmptyBox<>(null);
        } else {
            return new FullBox<>(something);
        }
    }
}
