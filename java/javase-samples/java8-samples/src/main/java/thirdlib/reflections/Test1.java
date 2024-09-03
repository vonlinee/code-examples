package thirdlib.reflections;

import org.reflections.Reflections;

public class Test1 {

    public static void main(String[] args) throws InterruptedException {
        Reflections reflections = new Reflections("com.my.project");

//		Set<Class<?>> subTypes =
//		  reflections.get(Test1.of(Test1.class).asClass());
//
//		Set<Class<?>> annotated = 
//		  reflections.get(SubTypes.of(TypesAnnotated.with(SomeAnnotation.class)).asClass());

        test1();
    }

    public static void test1() throws InterruptedException {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!this.isInterrupted()) {
                    System.out.println("thread is running");
                }
            }
        };
        thread.start();
        Thread.sleep(3000);
        System.out.println("设置中断标志为true");
        thread.interrupt();
        System.out.println("线程中断");
    }
}
