package io.devpl.sdk.restful;

public interface ResultBuilder<T> extends RestfulResultBuilder<Result<T>, ResultBuilder<T>> {

    ResultBuilder<T> setData(T data);

    default ResultBuilder<T> data(T data) {
        return setData(data);
    }
}