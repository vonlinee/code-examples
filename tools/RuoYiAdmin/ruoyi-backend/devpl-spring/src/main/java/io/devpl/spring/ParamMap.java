package io.devpl.spring;

import java.util.LinkedHashMap;

/**
 * 用作方法参数传递
 */
public final class ParamMap extends LinkedHashMap<String, Object> {

    public String getString(String key) {
        return (String) get(key);
    }

    public int getInt(String key) {
        return (int) get(key);
    }

    public Integer getInteger(String key) {
        return (Integer) get(key);
    }
}
