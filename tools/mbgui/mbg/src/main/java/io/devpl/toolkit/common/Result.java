package io.devpl.toolkit.common;

import io.devpl.toolkit.utils.JsonUtil;

import java.io.Serializable;

/**
 * 统一API响应结果封装
 */
public class Result<T> implements Serializable {

    /**
     * 响应码，200为成功，其它为错误
     */
    private int code;

    /**
     * 失败时的具体失败信息
     */
    private String message;

    /**
     * 响应的具体对象
     */
    private T data;

    public Result<T> setCode(ResultCode resultCode) {
        this.code = resultCode.code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Result<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JsonUtil.obj2json(this);
    }
}
