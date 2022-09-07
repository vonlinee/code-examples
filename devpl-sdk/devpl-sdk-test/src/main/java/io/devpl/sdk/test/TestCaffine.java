package io.devpl.sdk.test;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * TODO 封装日志门面
 */
public class TestCaffine {

    static final Logger logger = LoggerFactory.getLogger(TestCaffine.class);

    public static void main(String[] args) {

        System.out.println(logger.getClass()); // ch.qos.logback.classic.Logger

        logger.info("main");

        ch.qos.logback.classic.Logger log = (ch.qos.logback.classic.Logger) logger;

        log.debug("message", new RuntimeException());
    }

    /**
     * 手动加载
     * @param key
     * @return
     */
    public Object manulOperator(String key) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .maximumSize(10)
                .build();
        //如果一个key不存在，那么会进入指定的函数生成value
        Object value = cache.get(key, t -> setValue(key).apply(key));
        cache.put("hello", value);

        //判断是否存在如果不存返回null
        Object ifPresent = cache.getIfPresent(key);
        //移除一个key
        cache.invalidate(key);
        return value;
    }

    public Function<String, Object> setValue(String key) {
        return t -> key + "value";
    }
}
