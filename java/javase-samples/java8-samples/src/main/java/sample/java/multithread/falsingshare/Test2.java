package sample.java.multithread.falsingshare;

import org.openjdk.jol.info.ClassLayout;

/**
 * -XX:-RestrictContended
 */
public class Test2 {

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseInstance(new Counter()).toPrintable());
    }
}
