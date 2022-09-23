package io.devpl.sdk.internal.restful;

/**
 * 通过Builder模式构造Result<T>实例
 */
public interface ResultBuilder<T> extends RestfulResultBuilder<T, Result<T>, ResultBuilder<T>> {

    @Override
    default ResultBuilder<T> code(int code) {
        return this.setCode(code);
    }

    @Override
    default ResultBuilder<T> message(String message) {
        return this.setMessage(message);
    }

    @Override
    default ResultBuilder<T> stackTrace(String stackTrace) {
        return this.setStackTrace(stackTrace);
    }

    @Override
    default ResultBuilder<T> description(String description) {
        return this.setDescription(description);
    }

    @Override
    default ResultBuilder<T> throwable(Throwable throwable) {
        return this.setThrowable(throwable);
    }

    @Override
    default ResultBuilder<T> data(T data) {
        return this.setData(data);
    }

    @Override
    default ResultBuilder<T> getThis() {
        return this;
    }

    @Override
    Result<T> build();
}