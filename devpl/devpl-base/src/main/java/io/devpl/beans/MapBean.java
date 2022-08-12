package io.devpl.beans;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * joda-beans
 * 
 * @see java.util.HashMap
 * @author Administrator
 *
 */
public class MapBean implements DynamicBean, Serializable {

	private static final int INITIAL_CAPACITY = 5;

	private static final int MAX_FIELD_COUNT = 128;

	int size;

	/**
	 * 
	 */
	private static final long serialVersionUID = 5023764136825782801L;

	transient BeanField<Object>[] fields;

	public MapBean(Object bean) {
		if (bean == null) {
			throw new NullPointerException();
		}
		Class<? extends Object> clazz = bean.getClass();
		Field[] fields = clazz.getDeclaredFields();
		try {
			for (int i = 0; i < fields.length; i++) {
				fields[i].setAccessible(true);
				addFieldInternal(fields[i].getName(), fields[i].get(bean));
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	static final int hash(Object key) {
		int h;
		return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
	}

	@SuppressWarnings("unchecked")
	final BeanField<Object>[] resize() {
		// HashMap
		if (fields == null) {
			fields = (BeanField<Object>[]) new BeanField<?>[INITIAL_CAPACITY];
		}
		return fields;
	}

	@Override
	public <V> V set(String fieldName, V value, boolean addIfNotExists) throws NoSuchFieldException {
		ensureValidFieldName(fieldName);
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getKey().equals(fieldName)) {
				@SuppressWarnings("unchecked")
				V old = (V) fields[i].getValue();
				fields[i].setValue(value);
				return old;
			}
		}
		if (addIfNotExists) {
			// 不存在时新增
			addFieldInternal(fieldName, value);
		}
		throw new NoSuchFieldException();
	}

	<V> void addFieldInternal(String fieldName, V value) {
		if (size >= fields.length) {
			fields = resize();
		}
	}

	private void ensureValidFieldName(String fieldName) {
		if (fieldName == null || fieldName.length() == 0) {
			throw new IllegalArgumentException("name of field for a bean must be valid string !");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <V> V get(String fieldName) throws NoSuchFieldException {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getKey().equals(fieldName)) {
				return (V) fields[i].getValue();
			}
		}
		throw new NoSuchFieldException(fieldName);
	}

	@Override
	public void remove(String fieldName) {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getKey().equals(fieldName)) {
				fields[i] = null;
				size--;
			}
		}
	}

	@Override
	public boolean contains(String fieldName) {
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].getKey().equals(fieldName)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int count() {
		return size;
	}
}
