package org.example.springboot.common;

@FunctionalInterface
public interface Callback<T> {
	void call(T intput);
}
