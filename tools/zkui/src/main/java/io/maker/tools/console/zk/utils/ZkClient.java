package io.maker.tools.console.zk.utils;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.client.ZKClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ZkClient {

    private static final Logger log = LoggerFactory.getLogger(ZkClient.class);

    private static final int defaultRetryInterval = 30; // ms

    private ZooKeeper zk;
    private final ZKClientConfig zkConfig;

    public ZkClient() {
        this.zkConfig = new ZKClientConfig();
    }

    public ZkClient connect(String url, int sessionTimeout, int maxRetryTimes) {
        try {
            int retryTimes = 0;
            zk = new ZooKeeper(url, sessionTimeout, new ZkWatcher());
            while (zk.getState() != ZooKeeper.States.CONNECTED && retryTimes <= maxRetryTimes) {
                Thread.sleep(defaultRetryInterval);
                retryTimes++;
            }
            if (retryTimes >= 3) {
                log.error("failed to connect to zookeeper[{}], has tried {} times", url, maxRetryTimes);
            }
        } catch (IOException | InterruptedException e) {
            log.error("failed to connect to zookeeper[{}], has tried {} times, cause : {}", url, maxRetryTimes, e);
        }
        return this;
    }

    public ZooKeeper.States getState() {
        return zk.getState();
    }

    static class ZkWatcher implements Watcher {
        @Override
        public void process(WatchedEvent watchedEvent) {
            String path = watchedEvent.getPath();
            System.out.println(path);
        }
    }
}
