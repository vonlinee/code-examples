import io.devpl.eventbus.EventBus;
import io.devpl.eventbus.Subscribe;
import io.devpl.eventbus.ThreadMode;

public class Test {

    public static void main(String[] args) {
        EventBus bus = EventBus.builder()
                .eventInheritance(false)
                .build();
        bus.register(new Test());

        bus.post(2, result -> {
            if (result == null) {
                System.out.println(11111111);
            }
        });

        // new Thread(() -> {
        //     while (true) {
        //         try {
        //             Thread.sleep(1000);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //         bus.post(2);
        //     }
        // }, "thread-0").start();
        //
        // new Thread(() -> {
        //     while (true) {
        //         try {
        //             Thread.sleep(1000);
        //         } catch (InterruptedException e) {
        //             e.printStackTrace();
        //         }
        //         bus.post("11");
        //     }
        // }, "main").start();
    }

    @Subscribe
    public String method(Double i) {
        System.out.println(Thread.currentThread().getName() + "  method  " + i);
        return this.getClass().getName();
    }

    @Subscribe
    public Integer method2(Integer i) {
        System.out.println(Thread.currentThread().getName() + "  method2  " + i);
        // return this.getClass().getName();
        return i + 3;
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public String method3(String ps) {
        System.out.println(Thread.currentThread().getName() + "  method2  " + ps);
        return this.getClass().getName();
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public String method(Object object) {
        System.out.println(Thread.currentThread().getName() + "  method2  " + object);
        return this.getClass().getName();
    }
}
