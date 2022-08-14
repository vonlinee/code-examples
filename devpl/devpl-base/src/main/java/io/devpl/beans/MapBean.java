package io.devpl.beans;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * joda-beans
 * 基于ArrayMap的动态Bean
 * TODO 暂时使用List作为内部实现
 *
 * @author Administrator
 * @see java.util.HashMap
 * @since 0.0.0
 */
public class MapBean implements DynamicBean, Serializable, Comparable<MapBean> {

    /**
     * Bean的名称
     */
    private final String name;

    private static final int INITIAL_CAPACITY = 5;

    /**
     * 自定义序列化ID
     */
    private static final long serialVersionUID = 5023764136825782801L;

    // transient BeanField<Object>[] fields;
    transient List<BeanField<Object>> fields;

    public MapBean(String name) {
        this.name = name;
    }

    public MapBean(Object bean) {
        if (bean == null) {
            throw new NullPointerException();
        }
        Class<?> clazz = bean.getClass();
        this.name = clazz.getName();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                addFieldInternal(field.getName(), field.get(bean));
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    final BeanField<Object>[] resize() {
        return null;
    }

    @Override
    public <V> V set(String fieldName, V value, boolean addIfNotExists) throws NoSuchFieldException {
        ensureValidFieldName(fieldName);
        for (BeanField<Object> field : fields) {
            if (field.getKey().equals(fieldName)) {
                @SuppressWarnings("unchecked")
                V old = (V) field.getValue();
                field.setValue(value);
                return old;
            }
        }
        if (addIfNotExists) {
            // 不存在时新增
            addFieldInternal(fieldName, value);
        }
        throw new NoSuchFieldException();
    }

    /**
     * 待修改
     *
     * @param fieldName 字段名
     * @param value     字段值
     */
    private <V> void addFieldInternal(String fieldName, V value) {
        fields.add(new BeanField<>(hash(fieldName), fieldName, value, null));
    }

    /**
     * @param fieldName 字段名
     */
    private void ensureValidFieldName(String fieldName) {
        if (fieldName == null || fieldName.length() == 0) {
            throw new IllegalArgumentException("name of field for a bean must be valid string !");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(String fieldName) throws NoSuchFieldException {
        for (BeanField<Object> field : fields) {
            if (field.getKey().equals(fieldName)) {
                return (V) field.getValue();
            }
        }
        throw new NoSuchFieldException(fieldName);
    }

    @Override
    public void remove(String fieldName) {
        fields.removeIf(field -> field.getKey().equals(fieldName));
    }

    @Override
    public boolean contains(String fieldName) {
        return false;
    }

    @Override
    public int count() {
        return fields.size();
    }

    public Iterator<BeanField<Object>> iterator() {
        return fields.iterator();
    }

    @Override
    public int compareTo(MapBean o) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        if (name.length() == 0) return fields.toString();
        return name + " " + fields.toString();
    }
}
