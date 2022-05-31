package io.maker.codegen.core;

import io.maker.base.lang.MapBean;

import java.util.Map;

/**
 * 观察者，基于Map存储配置信息
 */
public abstract class Configuration {

    protected MapBean config;

    public Configuration(Map<String, Object> initConfig) {

    }

    abstract <R, P> void initialize(Callback<R, P> callback);

    abstract <R, P> void update(String configKey, Callback<R, P> callback);

    abstract <R, P> void refresh(Callback<R, P> callback);

    abstract <R, P> void remove(String configKey, Callback<R, P> callback);
}
