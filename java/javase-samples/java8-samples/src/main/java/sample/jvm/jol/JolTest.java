package sample.jvm.jol;

import org.openjdk.jol.info.ClassLayout;

import java.util.Objects;

public class JolTest {

    public static void main(String[] args) {
//        test2();
//        test3();
//        test4();
        test5();
    }


    public static void test1() {
        // System.out.println(VM.current().details());
        System.out.println(ClassLayout.parseClass(SimpleInt.class).toPrintable());
        System.out.println("====");
        System.out.println(ClassLayout.parseInstance(new SimpleInt()).toPrintable());
        new JolTest().hashCode();
    }

    public static void test2() {
        SimpleInt instance = new SimpleInt();
        System.out.println(ClassLayout.parseInstance(instance).toPrintable());
        //
        System.out.println(Objects.hashCode(instance));
        System.out.println(System.identityHashCode(instance));
        System.out.println(ClassLayout.parseInstance(instance).toPrintable());
    }

    public static void test3() {
        System.out.println(ClassLayout.parseClass(SimpleLong.class).toPrintable());
    }

    public static void test4() {
        System.out.println(ClassLayout.parseInstance(FieldsArrangement.class).toPrintable());
    }

    static final Object lock = new Object();

    public static void test5() {
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
    }
}
