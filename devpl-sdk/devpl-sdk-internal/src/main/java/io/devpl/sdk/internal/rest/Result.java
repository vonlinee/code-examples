package io.devpl.sdk.internal.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Restful风格的请求结果封装
 * <p>
 * 保存的数据使用泛型定义的话不太好优雅的实现
 * <p>
 * 避免泛型强转
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class Result extends ResultTemplate {

    private static final long serialVersionUID = -5385265639294136547L;

    private int code;
    private String message;
    private String description;
    private String stacktrace;
    private Object data;

    /**
     * 如果使用Jackson此字段还是会被序列化
     */
    private transient Throwable throwable;

    public Result() {
        super();
    }

    public static <E> ResultBuilder builder(List<E> list) {
        return new ListResult<>(list);
    }

}
