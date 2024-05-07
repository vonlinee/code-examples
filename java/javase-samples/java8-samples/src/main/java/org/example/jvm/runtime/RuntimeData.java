package org.example.jvm.runtime;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.List;

/**
 * https://www.modb.pro/db/129603
 * @author vonline
 * @since 2022-07-24 1:17
 */
public class RuntimeData {
    public static void main(String[] args) {
        test1();
    }

    //https://blog.csdn.net/timchen525/article/details/76039680
    public static void test1() {
        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        System.out.println(inputArguments);
    }

    public static void test2() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage usage = memoryMXBean.getHeapMemoryUsage();
        System.out.println("INT HEAP:" + usage.getInit() / 1024 / 1024 + "Mb");
        System.out.println("MAX HEAP:" + usage.getMax() / 1024 / 1024 + "Mb");
        System.out.println("USED HEAP:" + usage.getUsed() / 1024 / 1024 + "Mb");

        System.out.println("\nFull Information:");
        System.out.println("Heap Memory Usage:" + memoryMXBean.getHeapMemoryUsage());
        System.out.println("Non-Heap Memory Usage:" + memoryMXBean.getNonHeapMemoryUsage());

        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();
        System.out.println("=====================java options==================");
        System.out.println(inputArguments);

        System.out.println("=====================通过java来获取相关系统状态====================");
        long i = Runtime.getRuntime().totalMemory() / 1024 / 1024;//Java 虚拟机中的内存总量，以字节为单位
        System.out.println("总的内存量为:" + i + "Mb");
        long j = Runtime.getRuntime().freeMemory() / 1024 / 1024;//Java 虚拟机中的空闲内存量
        System.out.println("空闲内存量:" + j + "Mb");
        long k = Runtime.getRuntime().maxMemory() / 1024 / 1024;
        System.out.println("最大可用内存量:" + k + "Mb");
    }
}
