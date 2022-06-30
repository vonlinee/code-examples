package sample.redis.jedis;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.rules.Stopwatch;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.util.StopWatch;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class JedisClient {

    public static final String PASSWORD = "line@123!!";

    public static void main(String[] args) {
        // test2();
    }

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

    public static void test1() {
        Jedis jedis = getConnection();

        Set<String> keys1 = jedis.keys("*");
        System.out.println(keys1);
        System.out.println("=========================");

        // 如果 Redis 服务设置了密码，需要下面这行，没有就不需要
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: " + jedis.ping());
        jedis.set("name", "孙允珠");
        jedis.set("age", "29");
        String name = jedis.get("name");
        System.out.println(name);
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key + " => " + jedis.get(key));
        }
        String watch = jedis.watch("name");
        System.out.println(watch);
        String unwatch = jedis.unwatch();
    }

    public static void test2() {
        Jedis jedis = getConnection();
        // 先清空
        System.out.println("清空数据库 => " + jedis.flushDB()); // 返回OK
        Transaction tx = jedis.multi();
        try {
            tx.set("name", "zs");
            tx.set("age", "28");
            // int i = 1 / 0;  模仿事务
            tx.exec();  // 执行事务
        } catch (Exception exception) {
            System.out.println("放弃事务 => " + tx.discard()); // 返回OK
            exception.printStackTrace();
        } finally {
            System.out.println(jedis.get("name"));
            System.out.println(jedis.get("age"));
            jedis.close();
        }
    }

    public static void test3() {
        JedisPoolConfig jpc = new JedisPoolConfig();
        jpc.setBlockWhenExhausted(true);

    }

    /**
     * 上锁：SET resource-name anystring NX EX max-lock-time
     * @param resourceName
     * @param value
     */
    public void lock(Jedis jedis, String resourceName, String value, long milliseconds) {
        boolean lockFlag = false;
        while (!lockFlag) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lockFlag = jedis.setnx(resourceName, value) == 1;
            long expire = jedis.expire(resourceName, milliseconds);
        }
        System.out.println("上锁成功");
    }

    public void unlock(String resourceName) {

    }

    @Test
    public void test4() {
        Jedis jedis = getConnection();

        long result = jedis.setnx("name", "25");
        System.out.println(result);

    }

    @Test
    public void test5() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("获取连接");
        Jedis jedis = getConnection();
        stopWatch.stop();
        stopWatch.start("在内存中构造大数据");
        // 95.05 MB
        int size = 1000000;
        List<Model> models = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            models.add(new Model(i, "Model" + i, new Date(), "Address" + i));
        }
        stopWatch.stop();
        stopWatch.start("GSON序列化");
        Gson gson = new Gson();
        // System.out.println(ClassLayout.parseInstance(models).toPrintable());
        String s = gson.toJson(models);
        stopWatch.stop();
        stopWatch.start("插入大key");
        jedis.set("models", s);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    public void test6() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("获取连接");
        Jedis connection = getConnection();
        stopWatch.stop();
        stopWatch.start("删除大key操作"); // 37813300 ns = 37.8133 毫秒
        long times = connection.del("models");
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }
}
