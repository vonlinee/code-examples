package org.setamv.shardingsphere.sample.dynamic.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 操作结果DTO
 * @author setamv
 * @date 2021-04-20
 */
@Data
public class ResultDTO<T> implements Serializable {

    public static final String SUCCESS_CODE = "0000";
    public static final String ERROR_CODE = "9999";

    private String code;
    private String message;
    private T data;

    public ResultDTO(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <R> ResultDTO<R> success(R data) {
        return new ResultDTO<>(SUCCESS_CODE, null, data);
    }

    public static <R> ResultDTO<R> fail(String error) {
        return new ResultDTO<>(ERROR_CODE, error, null);
    }
}
