package sample.jvm.gc.collector;

/**
 * VM option:
 * -Xms20M -Xmx20M -XX:+PrintCommandLineFlags -XX:+UseParNewGC
 * 结果:
 * -XX:InitialHeapSize=20971520 -XX:MaxHeapSize=20971520 -XX:+PrintCommandLineFlags
 * -XX:+UseCompressedClassPointers -XX:+UseCompressedOops
 * -XX:-UseLargePagesIndividualAllocation -XX:+UseParNewGC
 *
 * @author vonline
 * @since 2022-08-01 12:12
 */
public class TestSerialGC {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(1000000);
    }
}
