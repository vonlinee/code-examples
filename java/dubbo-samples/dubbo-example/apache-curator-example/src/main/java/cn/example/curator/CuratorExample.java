package cn.example.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

/**
 * @author Brave
 * @create 2021-08-13 14:03
 * @description
 **/
public class CuratorExample {

    public static void main(String[] args) {
        String zookeeperConnectionString = "localhost:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        client.start();

        try {
            client.create().forPath("/", "Hello".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            //创建分布式锁, 锁空间的根节点路径为/curator/lock
            InterProcessMutex lock = new InterProcessMutex(client, "/curator/lock");
            if (lock.acquire(1000, TimeUnit.SECONDS)) {
                try {
                    // do some work inside of the critical section here
                    System.out.println("do some work inside of the critical section here");
                } finally {
                    lock.release();//完成业务流程, 释放锁
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
