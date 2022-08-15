package sample.jvm.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * -XX:-RestrictContended
 */
public class FalsingShare {

    public static void main(String[] args) {
        System.out.println(ClassLayout.parseClass(Isolated.class).toPrintable());
    }

}
