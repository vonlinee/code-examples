package io.devpl.webui.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * ResultTemplate参与序列化
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ResultTemplate extends Result<Object> implements Serializable {

    protected Integer code;

    protected String message;

    protected String description;

    /**
     * 异常调用栈
     */
    protected String stacktrace;

    /**
     * 自定义序列化为JSON字符串的方式
     * @return
     */
    public abstract String serialize();

    /**
     * 结果模板 Builder
     */
    protected static abstract class Builder {

        protected int code;

        protected String message;

        protected String description;

        /**
         * 异常调用栈
         */
        protected String stackTrace;

        /**
         * @return
         */
        abstract ResultTemplate build();
    }
}
