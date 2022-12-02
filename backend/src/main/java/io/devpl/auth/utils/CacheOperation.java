package io.devpl.auth.utils;

import org.springframework.beans.BeansException;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class CacheOperation implements ApplicationContextAware {

    CacheManager cacheManager;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CacheManager manager = applicationContext.getBean(CacheManager.class);
    }
}
