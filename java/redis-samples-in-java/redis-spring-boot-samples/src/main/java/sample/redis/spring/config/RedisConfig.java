package sample.redis.spring.config;

import org.apache.catalina.mbeans.ClassNameMBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        GenericJackson2JsonRedisSerializer json = new GenericJackson2JsonRedisSerializer();
        template.setDefaultSerializer(json);
        // 设置Key的序列化方式，采用字符串形式
        RedisSerializer<String> srs = new StringRedisSerializer();
        template.setKeySerializer(srs);
        template.setHashKeySerializer(srs);
        // 设置Value的序列化方式，采用JSON形式
        template.setValueSerializer(json);
        template.setHashValueSerializer(json);
        template.afterPropertiesSet();
        return template;
    }
}
