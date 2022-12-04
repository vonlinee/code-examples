package io.devpl.sdk.util;

import java.util.*;
import java.util.regex.Pattern;

/**
 * 采用Map结构实现
 */
final class DefaultDataObject implements DataObject {

    /**
     * 存放实际的数据
     */
    private ArrayMap<String, Object> data = null;

    /**
     * 严格模式，如果为true，针对每个数据项的key都要进行合法性校验
     */
    private final boolean strict;

    public DefaultDataObject(int initialCapacity) {
        this(true, initialCapacity);
    }

    public DefaultDataObject(boolean strict, int initialCapacity) {
        this.data = new ArrayMap<>(initialCapacity);
        this.strict = strict;
    }

    public DefaultDataObject(Map<String, Object> initialData) {
        this(true, initialData);
    }

    public DefaultDataObject(boolean strict, Map<String, Object> initialData) {
        if (initialData != null) {
            this.data = new ArrayMap<>(initialData.size());
            data.putAll(initialData);
        } else {
            this.data = new ArrayMap<>();
        }
        this.strict = strict;
    }

    /**
     * 校验name是否合法
     * @param name 数据项名称
     */
    private void ensureValidName(String name) {
        if (strict && !isAllowed(name)) {
            throw new IllegalArgumentException("illegal item name [" + name + "] of data!");
        }
    }

    @Override
    public boolean containsKey(String name) {
        return isAllowed(name) && data.containsKey(name);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public void setValue(String name, Object value) throws NoSuchElementException {
        if (!containsKey(name)) {
            throw new NoSuchElementException(name);
        }
        data.put(name, value);
    }

    @Override
    public DataObject put(String name, Object value) {
        ensureValidName(name);
        data.put(name, value);
        return this;
    }

    /**
     * 数据项的名称需要字母或者数字
     * @param name 数据项名称
     * @return 数据项名称是否符合规则
     */
    private boolean isAllowed(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V> V getValue(String name) {
        if (!isAllowed(name)) {
            return null;
        }
        return (V) data.get(name);
    }

    @Override
    public Iterator<DataItem> iterator() {
        final Set<Map.Entry<String, Object>> entries = data.entrySet();
        final List<DataItem> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : entries) {
            list.add(new DataItem(entry.getKey(), entry.getValue()));
        }
        return list.iterator();
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof DataObject) {
            return equals((DataObject) object);
        }
        return false;
    }

    @Override
    public String toString() {
        return "DataObject@" + Integer.toHexString(hashCode());
    }

    @Override
    public Map<String, Object> asMap() {
        return data;
    }

    @Override
    public DataObject copy() {
        return new DefaultDataObject(this.data);
    }
}