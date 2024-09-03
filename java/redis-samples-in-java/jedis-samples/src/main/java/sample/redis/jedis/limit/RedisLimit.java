package sample.redis.jedis.limit;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RedisLimit {

    public static Jedis getConnection() {
        JedisClientConfig config = DefaultJedisClientConfig.builder().clientName("test")
                // .password(PASSWORD)
                .socketTimeoutMillis(3000).build();
        //连接本地的 Redis 服务
        // 101.33.212.245
        Jedis jedis = new Jedis("localhost", 6379, config);
        if (!"PONG".equals(jedis.ping())) {
            throw new RuntimeException("连接失败");
        }
        return jedis;
    }

    ThreadPoolExecutor pool = new ThreadPoolExecutor(5, 5,
            0L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024));

    @Test
    public void testLimitRequest() {
        final Jedis jedis = getConnection();
        Queue<RequestTask> tasks = new ArrayDeque<>();
        for (int i = 0; i < 10; i++) {
            tasks.add(new RequestTask(jedis));
        }
        for (int i = 0; i < 1000; i++) {
            RequestTask task = tasks.poll();
            if (task != null) {
                pool.execute(task);
                tasks.add(task);
            }
        }
    }
}

class RequestTask implements Runnable {
    static final String KEY = "limit-window";// 窗口的key
    static final double WINDOW_SIZE = 10; // 窗口大小, 单位秒
    static final int MAX_LIMIT = 50; // 窗口范围内最大请求数
    final Jedis redisClient;

    public RequestTask(Jedis redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public void run() {
        // 使用秒级时间戳作为分数
        final double score = (double) System.currentTimeMillis() / 1000;
        String requestId = Thread.currentThread().getName();
        // 每次请求时，将当前时间戳（或请求时间戳）作为成员添加到有序集合中
        long count = redisClient.zadd(KEY, score, requestId);
        // 计算窗口的开始时间
        double start = score / 1000 - WINDOW_SIZE;
        // 检查并限制请求 比较[开始时间,当前时间]范围内的请求数量
        if (redisClient.zcount(KEY, start, score) > MAX_LIMIT) {
            System.out.println("拒绝请求");
        }
    }
}