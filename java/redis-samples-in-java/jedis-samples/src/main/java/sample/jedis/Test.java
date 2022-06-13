package sample.jedis;

import redis.clients.jedis.*;

import java.util.Set;

public class Test {

    public static final String PASSWORD = "line@123!!";

    public static void main(String[] args) {
        test2();
    }

    public static Jedis getConnection() {
        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .clientName("test")
                .password(PASSWORD)
                .socketTimeoutMillis(3000)
                .build();
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("101.33.212.245", 6379, config);
        if (!"PONG".equals(jedis.ping())) {
            throw new RuntimeException("连接失败");
        }
        return jedis;
    }

    public static void test1() {
        Jedis jedis = getConnection();
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
            int i = 1 / 0;
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
}
