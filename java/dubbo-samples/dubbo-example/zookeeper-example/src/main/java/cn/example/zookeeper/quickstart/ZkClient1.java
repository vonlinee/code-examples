package cn.example.zookeeper.quickstart;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author Brave
 * @create 2021-08-13 15:19
 * @description
 **/
public class ZkClient1 {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        ZookeeperWatcher watcher = new ZookeeperWatcher();
        ZooKeeper zk = watcher.zk;
        //等待zk连接成功的通知
        watcher.connectedSemaphore.await();
        //获取path目录节点的配置数据，并注册默认的监听器
        System.out.println(new String(zk.getData("/username", true, watcher.stat)));
        Thread.sleep(Integer.MAX_VALUE);
    }
}
