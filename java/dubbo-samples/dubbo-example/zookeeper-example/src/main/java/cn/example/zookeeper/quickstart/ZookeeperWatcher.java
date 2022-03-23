package cn.example.zookeeper.quickstart;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author Brave
 * @create 2021-08-13 15:09
 * @description
 **/
public class ZookeeperWatcher implements Watcher {

    public CountDownLatch connectedSemaphore = new CountDownLatch(1);
    public ZooKeeper zk;
    public Stat stat = new Stat();

    public ZookeeperWatcher() throws IOException {
        //zookeeper配置数据存放路径
        String path = "/username";
        String url = "localhost:2181";
        //连接zookeeper并且注册一个默认的监听器
        zk = new ZooKeeper(url, 5000, this);
    }

    @Override
    public void process(WatchedEvent event) {
        //zk连接成功通知事件
        if (Event.KeeperState.SyncConnected == event.getState()) {
            if (Event.EventType.None == event.getType() && null == event.getPath()) {
                connectedSemaphore.countDown();
            } else if (event.getType() == Event.EventType.NodeDataChanged) {
                try { //zk目录节点数据变化通知事件
                    byte[] data = zk.getData(event.getPath(), true, stat);
                    System.out.println("配置已修改，新值为：" + new String(data));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
