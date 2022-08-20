package org.example.springboot.rest;

public class EntityResult<T> extends Result<T> {
    @Override
    protected String serialize() {
        return null;
    }
}
