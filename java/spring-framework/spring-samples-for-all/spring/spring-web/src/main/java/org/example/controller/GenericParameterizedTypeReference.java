package org.example.controller;

import org.springframework.core.ParameterizedTypeReference;

import java.lang.reflect.Type;

public class GenericParameterizedTypeReference<T> extends ParameterizedTypeReference<T> {

    protected GenericParameterizedTypeReference() {

    }

    public static void main(String[] args) {
        GenericParameterizedTypeReference<Object> type = new GenericParameterizedTypeReference<>();

        Type type1 = type.getType();
        System.out.println(type1);
    }
}
