package org.example.springboot.support.json;

public interface JsonOperation<T> {
    String serialize(T obj);
    <T> T deserialize(String str);
}
