package io.devpl.sdk.internal.restful;

/**
 * 只能使用泛型定义了
 * @param <T> 结果类型
 */
public class Result<T> extends RestfullResult<T> implements ResultBuilder<T> {

    private static final long serialVersionUID = -5385265639294136547L;
    private T data;

    @Override
    public ResultBuilder<T> setCode(int code) {
        this.code = code;
        return this;
    }

    @Override
    public ResultBuilder<T> setMessage(String message) {
        this.message = message == null ? "" : message;
        return this;
    }

    @Override
    public ResultBuilder<T> setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace == null ? "" : stackTrace;
        return this;
    }

    @Override
    public ResultBuilder<T> setDescription(String description) {
        this.description = description == null ? "" : description;
        return this;
    }

    @Override
    public ResultBuilder<T> setThrowable(Throwable throwable) {
        return this;
    }

    @Override
    public ResultBuilder<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public Result<T> build() {
        return this;
    }

    public T getData() {
        return this.data;
    }

    @Override
    public Result<T> getThis() {
        return this;
    }

    @Override
    public int compareTo(RestfullResult<T> o) {
        return o == null ? 1 : Integer.compare(o.code, this.code);
    }
}
