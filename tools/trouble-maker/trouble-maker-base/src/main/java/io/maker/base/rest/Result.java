package io.maker.base.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 抽象结果类
 *
 * @param <T>
 */
public abstract class Result<T> implements Serializable {

    private static final long serialVersionUID = 2819999455116368072L;

    /**
     * 扩展信息，是否需要线程安全?
     */
    private final Map<String, Object> attachement = new HashMap<>();

    /**
     * 创建的时间戳
     */
    protected long timestamp;

    /**
     * 结果描述：包括状态码，以及信息
     */
    protected ResultDescription description;

    /**
     * 携带的数据
     */
    protected T data;

    Result() {
        this.timestamp = System.currentTimeMillis();
    }

    Result(ResultDescription description) {
        this.timestamp = System.currentTimeMillis();
        this.description = description;
    }

    public final T getData() {
        return data;
    }

    public final void setCode(int code) {
        this.description.code(code);
    }

    public final int getCode() {
        return this.description.code;
    }

    public final void setMessage(String message) {
        this.description.message(message);
    }

    public final String getMessage() {
        return this.description.message;
    }

    @SuppressWarnings("unchecked")
    public final <V> V detach(String name) {
        return (V) attachement.get(name);
    }

    public final void attach(String name, Object item) {
        this.attachement.put(name, item);
    }

    /**
     * Builder Pattern
     */
    interface ResultBuilder {
        void attach(String name, Object item);

        <V> V detach(String name);
    }
}
