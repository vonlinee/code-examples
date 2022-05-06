package code.sample.jedis;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisClientConfig;

public class Test {
    public static void main(String[] args) {

        String passwrod = "line@123!!";

        JedisClientConfig config = DefaultJedisClientConfig.builder()
                .clientName("test")
                .password(passwrod)
                .socketTimeoutMillis(3000)
                .build();

        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("101.33.212.245", 6379, config);
        // 如果 Redis 服务设置了密码，需要下面这行，没有就不需要
        System.out.println("连接成功");
        //查看服务是否运行
        System.out.println("服务正在运行: " + jedis.ping());


        jedis.set("name", "孙允珠");
        jedis.set("age", "29");
    }
}
