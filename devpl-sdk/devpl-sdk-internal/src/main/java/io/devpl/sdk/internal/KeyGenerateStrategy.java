package io.devpl.sdk.internal;

public interface KeyGenerateStrategy<K, T> {
    T key(K key);
}
