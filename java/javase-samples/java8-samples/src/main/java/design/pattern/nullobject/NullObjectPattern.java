package design.pattern.nullobject;

public class NullObjectPattern {
    public static void main(String[] args) {
        Object obj = new Object();
        Box<Object> box = Box.wrap(obj);
        box.doSomething();
    }
}
