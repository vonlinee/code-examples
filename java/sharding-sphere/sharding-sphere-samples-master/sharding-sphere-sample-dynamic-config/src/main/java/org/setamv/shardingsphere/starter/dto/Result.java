package org.setamv.shardingsphere.starter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @param <T>
 * @author setamv
 */
@Data
@AllArgsConstructor
public class Result<T> implements Serializable {

    public static final String CODE_SUCCESS = "0000";
    public static final String CODE_FAILURE = "9999";

    private String code;
    private String message;
    private T data;

    public static <T> Result success(T data) {
        return new Result(CODE_SUCCESS, "success", data);
    }

    public static <T> Result fail(T data) {
        return new Result(CODE_FAILURE, "error", data);
    }
}
