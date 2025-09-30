package org.lancoo.crm.controller;

import org.lancoo.crm.domain.GarbaceCollectorInfo;
import org.lancoo.crm.domain.JVMInfo;
import org.lancoo.crm.domain.MemoryPoolInfo;
import org.lancoo.crm.domain.MemoryUsageInfo;
import org.lancoo.crm.utils.GcUtils;

import java.lang.management.*;
import java.util.ArrayList;
import java.util.List;

public class JVMInfoExample {

    public static void main(String[] args) {
        JVMInfo jvmInfo = getJVMInfo(); // 获取 JVM 信息
        printJVMInfo(jvmInfo); // 打印 JVM 信息
    }

    public static JVMInfo getJVMInfo() {
        JVMInfo jvmInfo = new JVMInfo();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        // 获取和封装堆内存使用情况
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        jvmInfo.setHeapMemoryUsage(new MemoryUsageInfo(
                GcUtils.convertBytesToReadableSize(heapMemoryUsage.getInit()),
                GcUtils.convertBytesToReadableSize(heapMemoryUsage.getUsed()),
                GcUtils.convertBytesToReadableSize(heapMemoryUsage.getCommitted()),
                GcUtils.convertBytesToReadableSize(heapMemoryUsage.getMax())
        ));

        // 获取和封装非堆内存使用情况
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        jvmInfo.setNonHeapMemoryUsage(new MemoryUsageInfo(
                GcUtils.convertBytesToReadableSize(nonHeapMemoryUsage.getInit()),
                GcUtils.convertBytesToReadableSize(nonHeapMemoryUsage.getUsed()),
                GcUtils.convertBytesToReadableSize(nonHeapMemoryUsage.getCommitted()),
                GcUtils.convertBytesToReadableSize(nonHeapMemoryUsage.getMax())
        ));

        // 获取和封装垃圾收集器信息
        List<GarbaceCollectorInfo> gcInfoList = new ArrayList<>();
        for (GarbageCollectorMXBean gcBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            gcInfoList.add(new GarbaceCollectorInfo(
                    gcBean.getName(),
                    gcBean.getCollectionCount(),
                    gcBean.getCollectionTime()
            ));
        }
        jvmInfo.setGarbageCollectors(gcInfoList);

        // 获取和封装内存池信息
        List<MemoryPoolInfo> memoryPoolInfoList = new ArrayList<>();
        for (MemoryPoolMXBean pool : ManagementFactory.getMemoryPoolMXBeans()) {
            MemoryUsage usage = pool.getUsage();
            memoryPoolInfoList.add(new MemoryPoolInfo(
                    pool.getName(),
                    new MemoryUsageInfo(
                            GcUtils.convertBytesToReadableSize(usage.getInit()),
                            GcUtils.convertBytesToReadableSize(usage.getUsed()),
                            GcUtils.convertBytesToReadableSize(usage.getCommitted()),
                            GcUtils.convertBytesToReadableSize(usage.getMax())
                    )
            ));
        }
        jvmInfo.setMemoryPools(memoryPoolInfoList);

        return jvmInfo;
    }

    // 打印 JVM 信息的方法
    private static void printJVMInfo(JVMInfo jvmInfo) {
        System.out.println("Heap Memory Usage:");
        System.out.println("Initial: " + jvmInfo.getHeapMemoryUsage().getInitial());
        System.out.println("Used: " + jvmInfo.getHeapMemoryUsage().getUsed());
        System.out.println("Committed: " + jvmInfo.getHeapMemoryUsage().getCommitted());
        System.out.println("Max: " + jvmInfo.getHeapMemoryUsage().getMax());

        System.out.println("\nNon-Heap Memory Usage:");
        System.out.println("Initial: " + jvmInfo.getNonHeapMemoryUsage().getInitial());
        System.out.println("Used: " + jvmInfo.getNonHeapMemoryUsage().getUsed());
        System.out.println("Committed: " + jvmInfo.getNonHeapMemoryUsage().getCommitted());
        System.out.println("Max: " + jvmInfo.getNonHeapMemoryUsage().getMax());

        System.out.println("\nGarbage Collectors:");
        for (GarbaceCollectorInfo gc : jvmInfo.getGarbageCollectors()) {
            System.out.println("Name: " + gc.getName());
            System.out.println("Collection Count: " + gc.getCollectionCount());
            System.out.println("Collection Time (ms): " + gc.getCollectionTime());
        }

        System.out.println("\nMemory Pools:");
        for (MemoryPoolInfo pool : jvmInfo.getMemoryPools()) {
            System.out.println("Name: " + pool.getName());
            System.out.println("Usage - Initial: " + pool.getUsage().getInitial());
            System.out.println("Usage - Used: " + pool.getUsage().getUsed());
            System.out.println("Usage - Committed: " + pool.getUsage().getCommitted());
            System.out.println("Usage - Max: " + pool.getUsage().getMax());
        }
    }
}