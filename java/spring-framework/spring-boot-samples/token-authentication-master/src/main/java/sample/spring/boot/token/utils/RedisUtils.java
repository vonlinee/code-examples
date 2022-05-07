package sample.spring.boot.token.utils;

import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public void set(String key, String value, long expire) {
    	BoundValueOperations<String, String> ops = redisTemplate.boundValueOps(key);
    	ops.set(value, expire);
    	if (ops.persist()) {
			System.out.println("=============");
		}
    }
}
