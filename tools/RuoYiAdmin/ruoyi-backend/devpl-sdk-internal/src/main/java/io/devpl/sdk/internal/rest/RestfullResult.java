package io.devpl.sdk.internal.rest;

/**
 * RESTful风格的统一返回结果封装
 * @param <T> 提供一个泛型参数，由子类进行具体实现
 */
abstract class RestfullResult<T> extends ResultTemplate implements Comparable<RestfullResult<T>> {
    protected int code;
    protected String message;
    protected String description;
    protected String stackTrace;
    protected transient Throwable throwable;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
