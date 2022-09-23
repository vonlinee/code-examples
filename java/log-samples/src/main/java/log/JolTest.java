package log;

import org.openjdk.jol.info.ClassLayout;

public class JolTest {


    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(new A()).toPrintable());
        System.out.println();
        System.out.println(ClassLayout.parseInstance(new B()).toPrintable());
        System.out.println();
    }

}
