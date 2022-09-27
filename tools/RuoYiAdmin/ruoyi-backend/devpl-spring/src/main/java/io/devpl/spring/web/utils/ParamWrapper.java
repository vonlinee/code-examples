package io.devpl.spring.web.utils;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 设计要点
 * 1.非线程安全，只用于方法之间的参数传递，不需要线程安全
 * 2.快速访问能力，本身就是基于Map的结构
 *
 * @see org.springframework.util.MultiValueMap
 */
public interface ParamWrapper {

    boolean isEmpty();

    String[] names();

    <V> V[] values();

    int count();

    boolean exists(String name);

    <V> boolean exists(String name, Predicate<V> condition);

    <V> V get(String name);

    <V> V getOrElse(String name, V defaultValue);

    <V> V getOrElse(String name, Predicate<V> condition, V defaultValue);

    <V> V getOrElse(String name, Supplier<V> supplier);

    <V> V getOrElse(String name, Predicate<V> condition, Supplier<V> supplier);

    <V> V getOrThrow(String name);

    <V> V getOrThrow(String name, String message);

    <V> V getOrThrow(String name, Supplier<Throwable> throwable);

    <V> V getOrThrow(String name, Predicate<V> condition, String message);

    <V> V getOrThrow(String name, Predicate<V> condition, Supplier<Throwable> throwable);

    void set(String name, Object value);

    <V> Map<String, V[]> unwrap();
}
