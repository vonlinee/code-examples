package code.example.pattern.nullobject.v1;

public interface Null {
    default boolean isNull() {
        return true;
    }
}
