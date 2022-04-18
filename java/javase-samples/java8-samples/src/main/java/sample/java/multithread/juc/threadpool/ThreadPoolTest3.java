package sample.java.multithread.juc.threadpool;

/**
 * 线程池的submit吞异常
 * 
 * 为什么线程池的submit不抛出异常？
 * https://www.bilibili.com/video/BV1y44y1A7mr?spm_id_from=333.337.search-card.all.click
 */
public class ThreadPoolTest3 {

}

// 线程池提交execute()与submit()方法有什么区别？

// 区别就是submit可以拿到futruetask，而submit也是调用的execute。这里有个坑就是task.get会阻塞执行这条语句的线程，
// 直到对应的任务执行结束拿到结果

