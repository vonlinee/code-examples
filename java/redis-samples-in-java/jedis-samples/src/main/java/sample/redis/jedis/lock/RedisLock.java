package sample.redis.jedis.lock;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public abstract class RedisLock {

    // 初始化时区对象，北京时间是UTC+8，所以入参为8
    private final ZoneOffset zoneOffset = ZoneOffset.ofHours(8);

    protected String unix2JavaTimestamp(long unixTimeStamp) {
        LocalDateTime dt = LocalDateTime.ofEpochSecond(unixTimeStamp, 0, zoneOffset);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
        return formatter.format(dt);
    }

    private final int timeout = 60;

    protected long currentUnixStamp() {
        // 获取LocalDateTime对象对应时区的Unix时间戳
        return LocalDateTime.now().toEpochSecond(zoneOffset);
    }

    protected String createLockValue() {
        return Thread.currentThread().getName() + "-" + (currentUnixStamp() + timeout + 1);
    }

    private Jedis getConnection() {
        JedisClientConfig config = DefaultJedisClientConfig.builder().clientName("test")
                // .password(PASSWORD)
                .socketTimeoutMillis(3000).build();
        //连接本地的 Redis 服务
        // 101.33.212.245
        Jedis jedis = new Jedis("127.0.0.1", 6379, config);
        if (!"PONG".equals(jedis.ping())) {
            throw new RuntimeException("连接失败");
        }
        return jedis;
    }

    public void lock(String lockKey) {
        // 每次操作都重新获取，不要作为属性，使用局部变量存储，对象属性是共享的
        Jedis jedis = getConnection();
        lock(jedis, lockKey);
        jedis.close();
    }

    public void unlock(String lockKey) {
        Jedis jedis = getConnection();
        unlock(jedis, lockKey);
        jedis.close();
    }

    protected abstract void lock(Jedis jedis, String lockKey);

    protected abstract void unlock(Jedis jedis, String lockKey);
}
