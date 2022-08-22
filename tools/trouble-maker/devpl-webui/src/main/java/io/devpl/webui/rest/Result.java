package io.devpl.webui.rest;

import lombok.Data;

import java.io.Serializable;

/**
 * https://blog.csdn.net/TimeQuantum/article/details/107098749
 * 统一的返回结果封装
 * https://juejin.cn/post/7118885304957665288#%E4%B8%BA%E4%BB%80%E4%B9%88%E8%A6%81%E7%BB%9F%E4%B8%80%E5%B0%81%E8%A3%85%E6%8E%A5%E5%8F%A3
 *
 * @param <T>
 */
@Data
public abstract class Result<T> implements Serializable {

    /**
     * response timestamp.
     */
    private long timestamp;
    private String message;
    private int code;
    private String description;
    private String stackTrace;
    private T data;
}
