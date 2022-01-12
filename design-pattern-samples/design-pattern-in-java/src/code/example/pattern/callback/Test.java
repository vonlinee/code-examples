package code.example.pattern.callback;

public class Test {

    public static void main(String[] args) {
        Callback callback = () -> System.out.println("call back");
        Context context = new Context();
        context.setCallback(callback);
        context.doSomething();
    }
}
