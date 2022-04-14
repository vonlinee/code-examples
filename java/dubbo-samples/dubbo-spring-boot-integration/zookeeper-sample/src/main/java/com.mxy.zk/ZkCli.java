package com.mxy.zk;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class ZkCli {

    public static final ZkCli instance = new ZkCli();

    private static final Logger log = LoggerFactory.getLogger(ZkCli.class);

    // zookeeper-3.7.0
    public static final String ZOOKEEPER_ADDRESS = "101.33.212.245:2181";

    private final CuratorFramework curatorClient;
    // curator是zookeeper开源客户端
//    Stat就是对znode所有属性的一个映射，stat=null表示节点不存在
    private Stat stat;
    private InterProcessMultiLock interProcessMultiLock = null;
    private static final RetryPolicy DEFAULT_RETRY_POLICY = new ExponentialBackoffRetry(1000, 3);

    public ZkCli() {
        //sleep 3秒 重试 3次
        curatorClient = CuratorFrameworkFactory.builder().connectString(ZOOKEEPER_ADDRESS).sessionTimeoutMs(3000)    // 连接超时时间
                .connectionTimeoutMs(1000) // 会话超时时间
                .retryPolicy(new ExponentialBackoffRetry(1000, 3)).build(); //刚开始重试间隔为1秒，之后重试间隔逐渐增加，最多重试不超过三次
        curatorClient.start(); // 开始连接，使用curatorClient之前没有调用此会报错
        try {
            curatorClient.blockUntilConnected(1, TimeUnit.MINUTES); // 阻塞直到连接成功
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteIfExists(String path) {
        if (hasZnode(path)) {
            try {
                delete(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean hasZnode(String path) {
        try {
            return curatorClient.checkExists().forPath(path) == null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void create(String path, String data) throws Exception {
        if (hasZnode(path)) {
            log.info("节点{}已存在", path);
            return;
        }
        // 创建节点
        curatorClient.create().creatingParentsIfNeeded() // 若创建节点的父节点不存在则先创建父节点再创建子节点
                .withMode(CreateMode.PERSISTENT) // 创建的是持久节点
                .withACL(Ids.OPEN_ACL_UNSAFE) // 默认匿名权限,权限scheme id:'world,'anyone,:cdrwa
                .forPath(path, data.getBytes());
    }

    // 删除节点
    public void delete(String path) {
        try {
            curatorClient.delete().guaranteed() // 保障机制，若未删除成功，只要会话有效会在后台一直尝试删除
                    .deletingChildrenIfNeeded() // 若当前节点包含子节点，子节点也删除
                    .withVersion(stat.getCversion())//子节点版本号
                    .forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String query(String path) throws Exception {
        // 读取节点数据
        Stat stat = new Stat(); // Stat就是对znode所有属性的一个映射，stat=null表示节点不存在
        byte[] bytes = curatorClient.getData()
                .storingStatIn(stat) // 在获取节点内容的同时把状态信息存入Stat对象，如果不写的话只会读取节点数据
                .forPath(path);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void update(String path, String data) throws Exception {
        if (hasZnode(path)) {
            // 更新节点数据
            curatorClient.setData().withVersion(stat.getCversion()) // 乐观锁
                    .forPath(path, data.getBytes());
        }
    }

    @SneakyThrows
    public void lock(String path, CuratorFramework client) {
        interProcessMultiLock = new InterProcessMultiLock(client, Collections.singletonList(path));
        interProcessMultiLock.acquire();
    }

    public void unlock() {
        try {
            interProcessMultiLock.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 监听父节点以下所有的子节点, 当子节点发生变化的时候(增删改)都会监听到
    // 为子节点添加watcher事件
    public void watch(String path, boolean cacheData) throws Exception {
        // PathChildrenCache监听数据节点的增删改
        final PathChildrenCache childrenCache = new PathChildrenCache(curatorClient, path, cacheData);
        // NORMAL:异步初始化, BUILD_INITIAL_CACHE:同步初始化, POST_INITIALIZED_EVENT:异步初始化,初始化之后会触发事件
        childrenCache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        List<ChildData> childDataList = childrenCache.getCurrentData(); // 当前数据节点的子节点数据列表
        childrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                if (event.getType().equals(PathChildrenCacheEvent.Type.INITIALIZED)) {
                    log.info("------->监控：子节点初始化ok..");
                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_ADDED)) {
                    log.info("------->监控：添加子节点, path:{}, data:{}", event.getData().getPath(), event.getData().getData());
                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_REMOVED)) {
                    log.info("------->监控：删除子节点, path:{}", event.getData().getPath());
                } else if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                    log.info("------->监控：修改子节点, path:{}, data:{}", event.getData().getPath(), event.getData().getData());
                }
            }
        });
    }

    public void disconnect() {
        curatorClient.close();
    }

    public static ZkCli getInstance() {
        return instance;
    }

    public static void main(String[] args) {

        String path = "/";
        String pathAndNode = "/aa/bb/cc/dd";

        ZkCli zkCli = ZkCli.getInstance();
        try {
            //watch
            zkCli.watch(path, true);
            zkCli.deleteIfExists(pathAndNode);
            //1.create
            zkCli.create(pathAndNode, "mxy_create_node");
            Thread.sleep(2000);
            //2.query
            zkCli.query(pathAndNode);
            //3.update
            zkCli.update(pathAndNode, "mxy_update_node");
            Thread.sleep(1000);
            //4.del
            zkCli.delete(path);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            zkCli.disconnect();
        }
    }
}
