package org.example.java8.primary.generic;

public interface GenericInterface<T, R> {

    // Type parameter 'R' hides type parameter 'R'
    // Type parameter 'T' hides type parameter 'T'
    <T, R> R method1(T t);

    R method11(T t);

    R method2();

    <B> B method3();
}
