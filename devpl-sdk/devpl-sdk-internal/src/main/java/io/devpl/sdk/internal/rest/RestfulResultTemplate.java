package io.devpl.sdk.internal.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Restful风格的请求结果封装
 * <p>
 * 保存的数据使用泛型定义的话不太好优雅的实现
 * <p>
 * 避免泛型强转
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class RestfulResultTemplate extends ResultTemplate {

    private static final long serialVersionUID = -5385265639294136547L;

    private int code;
    private String message;
    private String description;
    private String stacktrace;

    /**
     * 如果使用Jackson还是会被序列化
     */
    private transient Throwable throwable;

    public RestfulResultTemplate() {
        super();
    }
}
