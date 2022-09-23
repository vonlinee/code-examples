package io.devpl.sdk.internal.rest;

/**
 * Builder模式
 * 不使用泛型，失去了在编译器就进行检查的能力
 * 每个方法都带有一个泛型参数
 */
public interface ResultBuilder<T> extends RestfulResultBuilder<ResultBuilder<T>> {

    @Override
    ResultBuilder<T> setCode(int code);
    @Override
    ResultBuilder<T> setMessage(String message);
    @Override
    ResultBuilder<T> setStackTrace(String stackTrace);
    @Override
    ResultBuilder<T> setDescription(String description);
    @Override
    ResultBuilder<T> setThrowable(Throwable throwable);

    ResultBuilder<T> setData(T data);

    @Override
    default ResultBuilder<T> code(int code) {
        return setCode(code);
    }

    @Override
    default ResultBuilder<T> message(String message) {
        return setMessage(message);
    }

    @Override
    default ResultBuilder<T> stackTrace(String stackTrace) {
        return setStackTrace(stackTrace);
    }

    @Override
    default ResultBuilder<T> description(String description) {
        return setDescription(description);
    }

    @Override
    default ResultBuilder<T> throwable(Throwable throwable) {
        return setThrowable(throwable);
    }

    default ResultBuilder<T> data(T data) {
        return setData(data);
    }

    @Override
    Result<T> build();
}