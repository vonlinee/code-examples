package io.devpl.spring.web.utils;

import java.util.function.Predicate;

/**
 * 前端请求参数封装
 * 支持多值
 * @see org.springframework.util.MultiValueMap
 */
public interface ParamMap {

    /**
     * 是否存在某个名字的参数
     * @param name
     * @return
     */
    boolean exists(String name);

    /**
     * 是否存在某个名字的参数，并且满足指定条件
     * @param name
     * @return
     */
    boolean exists(String name, Predicate<Object> rule);

    <V> V validate(String name, Predicate<V> rule);

    <V> V get(String name);

    <V> V[] getArray(String name);
}
