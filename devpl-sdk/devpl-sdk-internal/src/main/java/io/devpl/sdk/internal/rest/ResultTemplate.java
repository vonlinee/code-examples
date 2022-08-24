package io.devpl.sdk.internal.rest;

import java.io.Serializable;

public abstract class ResultTemplate implements Serializable {

    private static final long serialVersionUID = -6110075435780788111L;

    private long timestamp;

    public ResultTemplate() {
        this(System.currentTimeMillis());
    }

    private ResultTemplate(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * 一般不调用此方法，时间戳由构造函数生成
     * @param timestamp 系统时间戳 System.currentTimeMillis()
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
