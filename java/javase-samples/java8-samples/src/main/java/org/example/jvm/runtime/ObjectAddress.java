package org.example.jvm.runtime;

import org.openjdk.jol.vm.VM;

/**
 * @author vonline
 * @since 2022-07-28 7:30
 */
public class ObjectAddress {

    public static void main(String[] args) {
        test2();
    }

    public static void test1() {
        //要在 JVM 中查找特定对象的内存地址，我们可以使用 addressOf() 方法：
        String answer = "42";
        // org.openjdk.jol.vm.VM
        System.out.println(VM.current().addressOf(answer));
    }

    public static void test2() {
        Object obj = new Object();
        System.out.println("Memory address: " + VM.current().addressOf(obj));
        System.out.println("toString: " + obj);
        System.out.println("hashCode: " + obj.hashCode());
        System.out.println("hashCode: " + System.identityHashCode(obj));

        System.out.println(Integer.toHexString(obj.hashCode()));
    }

    public static void test3() {

    }
}
