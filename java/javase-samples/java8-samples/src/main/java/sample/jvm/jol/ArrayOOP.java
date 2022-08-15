package sample.jvm.jol;

import org.openjdk.jol.info.ClassLayout;

/**
 * -XX:-UseCompressedOops
 */
public class ArrayOOP {
    public static void main(String[] args) {
        boolean[] booleans = new boolean[3];
        System.out.println(ClassLayout.parseInstance(booleans).toPrintable());
    }
}
