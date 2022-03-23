package org.example.springboot.utils.function;

@FunctionalInterface
public interface ObjectSupplier<T> {
	T get();
}
