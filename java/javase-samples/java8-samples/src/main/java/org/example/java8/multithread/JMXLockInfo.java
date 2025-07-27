package org.example.java8.multithread;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class JMXLockInfo {
    public static void main(String[] args) throws Exception {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] threadIds = threadMXBean.getAllThreadIds();

        for (long threadId : threadIds) {
            ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
            if (threadInfo != null) {
                System.out.println("Thread ID: " + threadId);
                System.out.println("Thread Name: " + threadInfo.getThreadName());
                System.out.println("Thread State: " + threadInfo.getThreadState());
                if (threadInfo.getLockName() != null) {
                    System.out.println("Locked Object: " + threadInfo.getLockName());
                    System.out.println("Lock Owner ID: " + threadInfo.getLockOwnerId());
                    System.out.println("Lock Owner Name: " + threadInfo.getLockOwnerName());
                }
                System.out.println("-------------------------");
            }
        }
    }
}