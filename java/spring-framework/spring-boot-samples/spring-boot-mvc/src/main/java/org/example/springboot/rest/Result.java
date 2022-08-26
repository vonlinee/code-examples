package org.example.springboot.rest;

import lombok.Data;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * TODO 是否需要定义此泛型
 * @param <T>
 */
@Data
public abstract class Result<T> implements Serializable {

    /**
     * 泛型类型
     */
    protected transient Class<?> type;

    /**
     *
     */
    private static final long serialVersionUID = -8939479680323102941L;

    protected int code;
    protected String msg;
    protected T data;

    /**
     * 时间戳
     */
    protected long timestamp;

    /**
     * 异常调用栈
     */
    protected String stackTrace;

    /**
     * 序列化
     * @return
     */
    protected abstract String serialize();


}
