package org.example;

import org.junit.Test;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

public class Main {
    public static void main(String[] args) {

    }

    public static RedissonClient client() {
        Config config = new Config();
        config.setTransportMode(TransportMode.EPOLL); // 默认是NIO的方式
        config.useClusterServers()
                //可以用"rediss://"来启用SSL连接，前缀必须是redis:// or rediss://
                .addNodeAddress("redis://127.0.0.1:7181");
        return Redisson.create(config);
    }

    @Test
    public void test1() {
        RedissonClient client = client();

    }

    public void test() {
        // 获取锁
        RLock lock = client().getLock("my_lock");

        lock.lock();

        lock.unlock();
    }
}