package io.devpl.sdk.restful;

import java.util.List;

// 工具类方法
public class Results {

    public static <T> Result<T> of(StatusCode status, String toast, T data) {
        return new Result<>(status.getCode(), status.getMessage(), data, toast);
    }

    public static <T> Result<T> of(int code, String message, String toast, T data) {
        return new Result<>(code, message, data, toast);
    }

    public static <T> ListResult<T> list(StatusCode status, String toast, List<T> data) {
        return new ListResult<>(status.getCode(), status.getMessage(), data, toast);
    }

    public static <T> ListResult<T> list(int code, String message, String toast, List<T> data) {
        return new ListResult<>(code, message, data, toast);
    }

    public static <T> ResultBuilder<T> builder() {
        return new Result<>();
    }

    public static <T> ListResultBuilder<T> listBuilder() {
        return new ListResult<>();
    }
}
