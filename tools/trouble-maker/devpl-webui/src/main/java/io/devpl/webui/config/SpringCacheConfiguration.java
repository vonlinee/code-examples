package io.devpl.webui.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Arrays;

@Configuration
public class SpringCacheConfiguration {

    private static class DefaultKeyGenerator implements KeyGenerator {
        @Override
        public Object generate(Object target, Method method, Object... params) {
            return method.getName() + "[" + Arrays.asList(params) + "]";
        }
    }

    @Bean(value = {"cacheKeyGenerator"})
    public KeyGenerator keyGenerator() {
        return new DefaultKeyGenerator();
    }
}