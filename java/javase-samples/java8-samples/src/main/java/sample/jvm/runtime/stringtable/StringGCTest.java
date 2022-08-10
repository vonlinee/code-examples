package sample.jvm.runtime.stringtable;

/**
 * -Xms15m -Xmx15m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails
 * @author vonline
 * @since 2022-07-27 16:07
 */
public class StringGCTest {

    public static void main(String[] args) {
        int times = 100;
        for (int i = 0; i < times; i++) {
            String.valueOf(i).intern();
        }
    }
}
