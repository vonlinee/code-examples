package design.pattern.callback;

/**
 * 回调可以将控制权转移
 * 两者都持有互相的引用
 * 同时需要一个第三者全，来触发对应的事件
 * <p>
 * 比如我们手动调用其中一方的方法，出发对应的回调
 */
public class Test {

    public static void main(String[] args) {
        Callback callback = () -> System.out.println("call back");
        Context context = new Context();
        context.setCallback(callback);
        context.doSomething();
    }
}
