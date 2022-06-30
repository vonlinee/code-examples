package sample.redis.jedis;

/**
 * @since created on 2022年6月21日
 */
public interface RedisClient {
    RedisClient connect(String ip, int port);
}
