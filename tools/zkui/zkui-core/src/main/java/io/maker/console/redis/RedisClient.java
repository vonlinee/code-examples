package io.maker.console.redis;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class RedisClient implements InitializingBean {

    @Autowired
    StringRedisTemplate template;

    public String get(String key) {
        return template.boundValueOps(key).get();
    }

    public void set(String key, String value, long timeout, TimeUnit unit) {
        template.boundValueOps(key).set(value, timeout, unit);
    }

    public void set(String key, String value, Duration timeout) {
        template.boundValueOps(key).set(value, timeout);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (template.getDefaultSerializer() != null) {
            System.out.println(template.getDefaultSerializer());
        }
        template.setEnableDefaultSerializer(true);
        template.setDefaultSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    }
}
