package code.example.pattern.factory;

public class ObjectFactory<T> {

    private boolean singleton;

    public static void create(boolean singleton) {

    }

    public T get() {
        if (singleton) {

        }
        return null;
    }
}
