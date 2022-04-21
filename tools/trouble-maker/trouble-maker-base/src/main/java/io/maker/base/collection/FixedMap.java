package io.maker.base.collection;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import io.maker.base.annotation.Nullable;

/**
 * TODO 换成数组实现而不是List,虽然List也是基于数组 如果key为null则在数组第一个元素位置 Thread-Safe
 *
 * @param <K>
 * @param <V>
 */
public class FixedMap<K, V> implements Map<K, V> {

	private final AtomicInteger cursor = new AtomicInteger(0);

	private final Object[] keys;
	private final Object[] values;

	private int size; // 存放元素个数

	public FixedMap(int intialCapacity) {
		this.keys = new Object[intialCapacity];
		this.values = new Object[intialCapacity];
	}

	@Override
	public int size() {
		return keys.length;
	}

	@Override
	public boolean isEmpty() {
		return keys.length == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		return true;
	}

	@Override
	public boolean containsValue(Object value) {
		return Arrays.binarySearch(values, value) > 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object key) {
		int i = Arrays.binarySearch(keys, key);
		return (V) values[i];
	}

	@Override
	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		// Non-atomic operations on volatile fields are operations where the volatile
		// field
		// is read and the value is used to update the volatile field. It is possible
		// for the
		// value of the field to change between the read and write, making the operation
		// possibly invalid.
		// In such cases it is better to surround the operation with a synchronized
		// block or make
		// use of one of the Atomic** or Atomic**FieldUpdater classes from the
		// java.util.concurrent.atomic package.
		// cursor = cursor + 1;
		Object oldValue = values[cursor.get()];
		int cursorIndex = cursor.addAndGet(1);
		synchronized (this) {
			ensureCapacityFixed();
			keys[cursorIndex] = key;
			values[cursorIndex] = value;
		}
		return (V) oldValue;
	}

	private void ensureCapacityFixed() {
		if (cursor.get() >= values.length) {
			throw new UnsupportedOperationException("the capacity of FixedMap is fixed with value " + values.length);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public V remove(Object key) {
		int i = Arrays.binarySearch(keys, key);
		if (i < 0) {
			return null;
		}
		V value = (V) values[i];
		values[i] = null;
		return value;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (K key : m.keySet()) {
			put(key, m.get(key));
		}
	}

	@Override
	public void clear() {
		for (int i = 0; i < values.length; i++) {
			keys[i] = null;
			values[i] = null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Set<K> keySet() {
		return new HashSet<K>((Collection<? extends K>) Arrays.asList(keys));
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		return (Collection<V>) Arrays.asList(values);
	}

	@Override
	@SuppressWarnings("uncheced")
	public Set<Entry<K, V>> entrySet() {
		HashSet<Entry<K, V>> entrySet = new HashSet<>();
		for (int i = 0; i < keys.length; i++) {
			entrySet.add(new AbstractMap.SimpleEntry<K, V>((K) keys[i], (V) values[i]));
		}
		return Collections.unmodifiableSet(entrySet);
	}

	@Override
	public V getOrDefault(Object key, V defaultValue) {
		return Map.super.getOrDefault(key, defaultValue);
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) {
		Map.super.forEach(action);
	}

	@Override
	public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
		Map.super.replaceAll(function);
	}

	@Nullable
	@Override
	public V putIfAbsent(K key, V value) {
		return Map.super.putIfAbsent(key, value);
	}

	@Override
	public boolean remove(Object key, Object value) {
		return Map.super.remove(key, value);
	}

	@Override
	public boolean replace(K key, V oldValue, V newValue) {
		return Map.super.replace(key, oldValue, newValue);
	}

	@Nullable
	@Override
	public V replace(K key, V value) {
		return Map.super.replace(key, value);
	}

	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		return Map.super.computeIfAbsent(key, mappingFunction);
	}

	@Override
	public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return Map.super.computeIfPresent(key, remappingFunction);
	}

	@Override
	public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
		return Map.super.compute(key, remappingFunction);
	}

	@Override
	public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
		return Map.super.merge(key, value, remappingFunction);
	}
}
