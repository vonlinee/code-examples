package io.devpl.webui.rest;

public class Results {

    public static <T> ListResult<T> list() {
        return ListResult.builder();
    }

    public static <T> EntityResult<T> entity() {
        return EntityResult.builder();
    }
}
