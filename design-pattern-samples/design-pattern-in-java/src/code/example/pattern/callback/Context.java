package code.example.pattern.callback;

public class Context {

    private Callback callback;

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void doSomething() {
        System.out.println("do something");
        callback.call();
    }
}
