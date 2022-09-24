package io.devpl.sdk.restful;

import java.io.Serializable;

/**
 * 通用结果模板，附带一个时间戳以及一个异常基类 Throwable
 * @since 0.0.1
 */
public abstract class ResultTemplate implements Serializable {

    private static final long serialVersionUID = -6110075435780788111L;

    private final long timestamp;
    protected String stackTrace;

    /**
     * 用于处理异常堆栈信息
     */
    protected transient Throwable throwable;

    public ResultTemplate() {
        this(System.currentTimeMillis());
    }

    private ResultTemplate(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
