package io.devpl.spring.boot.database;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.sql.init.dependency.DatabaseInitializerDetector;
import org.springframework.core.Ordered;

import java.util.HashSet;
import java.util.Set;

/**
 * 数据库初始化
 * https://pxzxj.github.io/articles/starter-data-initialization.html
 */
public class DevplDbInitializerDetector implements DatabaseInitializerDetector {

    /**
     * 返回用来初始化数据库的Bean名称
     *
     * @param beanFactory
     * @return
     */
    @Override
    public Set<String> detect(ConfigurableListableBeanFactory beanFactory) {
        return new HashSet<>();
    }

    /**
     * detect()完成的回调方法
     *
     * @param beanFactory
     * @param dataSourceInitializerNames
     */
    @Override
    public void detectionComplete(ConfigurableListableBeanFactory beanFactory, Set<String> dataSourceInitializerNames) {

    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
