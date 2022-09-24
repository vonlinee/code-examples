package io.devpl.sdk.restful;

import java.util.List;

/**
 * 工具类方法
 *
 * @since 0.0.1
 */
public abstract class Results {

    public static <T> Result<T> of(Status status, String toast, T data) {
        return new Result<>(status.getCode(), status.getMessage(), data, toast);
    }

    public static <T> Result<T> of(int code, String message, String toast, T data) {
        return new Result<>(code, message, data, toast);
    }

    public static <T> ListResult<T> list(Status status, String toast, List<T> data) {
        return new ListResult<>(status.getCode(), status.getMessage(), data, toast);
    }

    public static <T> ListResult<T> list(int code, String message, String toast, List<T> data) {
        return new ListResult<>(code, message, data, toast);
    }

    public static <T> RBuilder<T> builder() {
        return new Result<>();
    }

    public static <T> ListRBuilder<T> listBuilder() {
        return new ListResult<>();
    }

    @SuppressWarnings("unchecked")
    public static <T> RBuilder<T> mapBuilder() {
        return (RBuilder<T>) new MResult();
    }

    @SuppressWarnings("unchecked")
    public static <T> ListRBuilder<T> mapListBuilder() {
        return (ListRBuilder<T>) new MLResult();
    }


}
