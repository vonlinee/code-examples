package io.devpl.spring.web.utils;

import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * name -> hash
 */
public class ParamMapWrapper implements ParamWrapper {

    protected String[] names;

    protected Object[][] values;

    @Override
    public boolean isEmpty() {
        return names.length == 0;
    }

    @Override
    public String[] names() {
        return names;
    }

    @Override
    public <V> V[] values() {
        return (V[]) values;
    }

    @Override
    public int count() {
        return names.length;
    }

    @Override
    public boolean exists(String name) {
        return false;
    }

    @Override
    public <V> boolean exists(String name, Predicate<V> condition) {
        return false;
    }

    @Override
    public <V> V get(String name) {
        return null;
    }

    @Override
    public <V> V getOrElse(String name, V defaultValue) {
        return null;
    }

    @Override
    public <V> V getOrElse(String name, Predicate<V> condition, V defaultValue) {
        return null;
    }

    @Override
    public <V> V getOrElse(String name, Supplier<V> supplier) {
        return null;
    }

    @Override
    public <V> V getOrElse(String name, Predicate<V> condition, Supplier<V> supplier) {
        return null;
    }

    @Override
    public <V> V getOrThrow(String name) {
        return null;
    }

    @Override
    public <V> V getOrThrow(String name, String message) {
        return null;
    }

    @Override
    public <V> V getOrThrow(String name, Supplier<Throwable> throwable) {
        return null;
    }

    @Override
    public <V> V getOrThrow(String name, Predicate<V> condition, String message) {
        return null;
    }

    @Override
    public <V> V getOrThrow(String name, Predicate<V> condition, Supplier<Throwable> throwable) {
        return null;
    }

    @Override
    public void set(String name, Object value) {

    }

    @Override
    public <V> Map<String, V[]> unwrap() {
        return null;
    }
}
